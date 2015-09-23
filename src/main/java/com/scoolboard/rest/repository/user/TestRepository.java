package com.scoolboard.rest.repository.user;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.*;
import com.scoolboard.rest.entity.AbstractDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akasha on 9/23/15.
 */
@Slf4j
@Repository
public class TestRepository<TDO extends AbstractDO> {

    @Autowired
    CouchbaseClient cb;

    public Iterable<TDO> all() {
        View view = cb.getView("user", "all");
        Query query = new Query();
        int docsPerPage = 1;

        Paginator paginatedQuery = cb.paginatedQuery(view, query, docsPerPage);
        int pageCount = 0;
        List<String> results = new ArrayList<String>();
        while (paginatedQuery.hasNext()) {
            pageCount++;
            log.info(" -- Page " + pageCount + " -- ");
            ViewResponse response = paginatedQuery.next();
            for (ViewRow row : response) {
                System.out.println(row.getKey() + " : " + row.getId());
                results.add(row.getValue());
            }
        }
        log.info("results " + results);
        //need a mapper to parse string to objects here
        return null;
    }
}
