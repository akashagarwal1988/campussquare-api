package com.scoolboard.rest.common.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import lombok.Getter;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;
import scala.Array;

/**
 * Created by akasha on 2/3/15.
 */
@Component
public class RegexValidator extends Validator<Object, Object, Regex> {
    private static final String[] schemas = new String[]{"http", "https"};
    private static final UrlValidator urlValidator = new UrlValidator(schemas);

    @Override
    public Optional<Message> validate(Object value, Object model, Regex annotation) {
        List<String> regex = new ArrayList<>();
        if (annotation.email()) regex.add("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$");
        if (annotation.phone())
            regex.add("^([+]?1\\s*[-\\/.]?\\s*)?(((\\d{3}))|(\\d{3}))\\s*[-\\/.]?\\s*(\\d{3})\\s*[-\\/.]?\\s*(\\d{4})\\s*(([xX]|[eE][xX][tT]?[.]?|extension)\\s*([#*\\d]+))*$");
        if (annotation.url()) regex.add("^" +
                "(?:(?:https?)://)" +
                "(?:" +
                "(?!10(?:\\.\\d{1,3}){3})" +
                "(?!127(?:\\.\\d{1,3}){3})" +
                "(?!169\\.254(?:\\.\\d{1,3}){2})" +
                "(?!192\\.168(?:\\.\\d{1,3}){2})" +
                "(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})" +
                "(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])" +
                "(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}" +
                "(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))" +
                "|" +
                "(?:xn--[a-z0-9\\-]{1,59}|(?:(?:[a-z\\u00a1-\\uffff0-9]+-?){0,62}[a-z\\u00a1-\\uffff0-9]{1,63}))" +
                "(?:\\.(?:xn--[a-z0-9\\-]{1,59}|(?:[a-z\\u00a1-\\uffff0-9]+-?){0,62}[a-z\\u00a1-\\uffff0-9]{1,63}))*" +
                "(?:\\.(?:xn--[a-z0-9\\-]{1,59}|(?:[a-z\\u00a1-\\uffff]{2,63})))" +
                ")" +
                "(?::\\d{2,5})?" +
                "(?:/[^\\s]*)?" +
                "$");

        if (annotation.urlWithProtocol())
            regex.add("^[hH][tT][tT][pP][sS]?:\\/\\/([\\da-zA-Z\\.-]+)\\.([a-zA-Z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$");
        regex.addAll(Arrays.asList(annotation.regex()));
        if (value instanceof String) {
            if (annotation.urlWithProtocol()) return validateURL((String) value);
            else return validateString((String) value, annotation, regex);
        } else if (value == null || value instanceof Optional<?>)
            return validateRequired(value, annotation.required(), annotation.message());
        else throw new RuntimeException("Validator applied to a not supported property");
    }

    private Optional<Message> validateURL(String url) {
        if (urlValidator.isValid(url)) return Optional.empty();
        else
            return Optional.of(new Message("property.url", String.format("This URL(%s) is not in correct format", url)));
    }

    private Optional<Message> validateString(String value, Regex annotation, List<String> regex) {
        Optional<Message> requiredFailed = validateRequired(value, annotation.required(), annotation.message());
        if (requiredFailed.isPresent()) return requiredFailed;
        else if (value != null && !value.isEmpty()) {
            List<Boolean> result = regex.stream().map(exp -> Pattern.compile(exp).matcher(value).matches()).collect(Collectors.toList());
            //if reg expression are inclusive (all must match) and at least one fail
            if ((annotation.inclusive() && result.contains(false)) ||
                    !annotation.inclusive() && !result.contains(false))
                return Optional.of(new Message("property.regex", "This field is not in correct format."));
            else return Optional.empty();
        } else return Optional.empty();
    }
}
