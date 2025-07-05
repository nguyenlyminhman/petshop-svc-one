package com.pts.common;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Getter
@Setter
@Service
public abstract class AbstractService {

    @Autowired
    protected HttpServletRequest httpServletRequest;

    @PersistenceContext(unitName = "primaryEntityManager")
    public EntityManager primaryEntityManager;

    @PersistenceContext(unitName = "catEntityManager")
    public EntityManager catEntityManager;

    @PersistenceContext(unitName = "firstMysqlEntityManager")
    public EntityManager firstMysqlEntityManager;

}