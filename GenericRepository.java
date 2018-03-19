/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author kasun_n
 */
@Repository("genericRepository")
public class GenericRepository {

    /*-----------------------------
    Dependancy Injection
    -----------------------------*/
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //save method
    public Serializable save(Object object) throws Exception {
        Serializable res = 0l;//define method response varible

        //get current session using session factory       
        Session session = sessionFactory.getCurrentSession();

        //save object using session
        res = (Serializable) session.save(object);

        //return response
        return res;

    }

    //update method
    public Object update(Object object) throws Exception {
        int res = 0;//define method response varible
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();

        //update object using session
        session.update(object);

        res = 1;
        //return response
        return res;

    }

    //get method
    public Object get(int id, Class cls) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();

        //get obejct using session
        Object object = session.get(cls, id);

        //return object
        return object;
    }

    //get method
    public Object get(String id, Class cls) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();

        //get obejct using session
        Object object = session.get(cls, id);

        //return object
        return object;
    }

    //get method by where
    public Object get(Class cls, WhereStatement... wheres) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //Add where clause
        for (WhereStatement where : wheres) {
            criteria.add(Restrictions.eq(where.getProperty(), where.getValue()));
        }

        //return list
        List list = criteria.list();
        if (list.size() > 0) {
            return list.get(0);
        }

        return null;
    }

    //delete method
    public int delete(Object object) throws Exception {
        int res = 0;//define method response varible
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();

        //delete object using session
        session.delete(object);
        res = 1;

        //return response
        return res;

    }

    public List list(Class cls, String orderProperty, String orderType) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //set order by
        if (orderType.equals(SystemVarList.ASC)) {
            criteria.addOrder(Order.asc(orderProperty));
        } else {
            criteria.addOrder(Order.desc(orderProperty));
        }

        //return list
        return criteria.list();

    }

    public List list(Class cls) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //return list
        return criteria.list();

    }

    //delete Header detals item lines
    public int deleteRecords(Class c, WhereStatement... whereStatements) {
        Session s = sessionFactory.getCurrentSession();
        String whereString = "";
        String hql = "DELETE FROM " + c.getSimpleName();
        //build where statment
        for (int i = 0; i < whereStatements.length; i++) {
            WhereStatement whereStatement = whereStatements[i];
            if (i == 0) {              
                whereString = whereString+" WHERE " + whereStatement.getProperty() + "='" + whereStatement.getValue() + "'";
            } else {
                whereString = whereString+" AND " + whereStatement.getProperty() + "='" + whereStatement.getValue() + "'";
            }

        }
        Query query = s.createQuery(hql + whereString);
        return query.executeUpdate();
    }

    //list by multiple wheres using AND
    public List list(Class cls, String orderProperty, String orderType, WhereStatement... wheres) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //Add where clause
        for (WhereStatement where : wheres) {
            //set operator
            switch (where.getOperator()) {
                case "=":
                    criteria.add(Restrictions.eq(where.getProperty(), where.getValue()));
                    break;

                case "like":
                    criteria.add(Restrictions.like(where.getProperty(), where.getValue()));
                    break;

                case "<>":
                    criteria.add(Restrictions.ne(where.getProperty(), where.getValue()));
                    break;

                case "IS NULL":
                    criteria.add(Restrictions.isNull(where.getProperty()));
                    break;

                case "IS NOT NULL":
                    criteria.add(Restrictions.isNotNull(where.getProperty()));
                    break;

                case "less_than":
                    criteria.add(Restrictions.lt(where.getProperty(), where.getValue()));
                    break;

                case "greater_than":
                    criteria.add(Restrictions.gt(where.getProperty(), where.getValue()));
                    break;

            }
        }

        //set order by
        if (orderType.equals(SystemVarList.ASC)) {
            criteria.addOrder(Order.asc(orderProperty));
        } else {
            criteria.addOrder(Order.desc(orderProperty));
        }

        //return list
        return criteria.list();

    }

    //list by multiple wheres using AND
    public List list(Class cls, WhereStatement... wheres) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //Add where clause
        for (WhereStatement where : wheres) {
            //set operator
            switch (where.getOperator()) {
                case "=":
                    criteria.add(Restrictions.eq(where.getProperty(), where.getValue()));
                    break;

                case "like":
                    criteria.add(Restrictions.like(where.getProperty(), where.getValue()));
                    break;

                case "<>":
                    criteria.add(Restrictions.ne(where.getProperty(), where.getValue()));
                    break;

                case "IS NULL":
                    criteria.add(Restrictions.isNull(where.getProperty()));
                    break;

                case "IS NOT NULL":
                    criteria.add(Restrictions.isNotNull(where.getProperty()));
                    break;

                case "less_than":
                    criteria.add(Restrictions.lt(where.getProperty(), where.getValue()));
                    break;

                case "greater_than":
                    criteria.add(Restrictions.gt(where.getProperty(), where.getValue()));
                    break;

            }
        }

        //return list
        return criteria.list();

    }

    //list with or function
    public List listOR(Class cls, WhereStatement... wheres) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //Add where clause
        for (WhereStatement where : wheres) {
            //set operator
            switch (where.getOperator()) {
                case "=":
                    criteria.add(Restrictions.or(Restrictions.eq(where.getProperty(), where.getValue())));
                    break;
                case "like":
                    criteria.add(Restrictions.or(Restrictions.eq(where.getProperty(), where.getValue())));
                    break;
                case "<>":
                    criteria.add(Restrictions.or(Restrictions.eq(where.getProperty(), where.getValue())));
                    break;
                case "IS NULL":
                    criteria.add(Restrictions.or(Restrictions.eq(where.getProperty(), where.getValue())));
                    break;
                case "IS NOT NULL":
                    criteria.add(Restrictions.or(Restrictions.eq(where.getProperty(), where.getValue())));
                    break;

            }
        }

        //return list
        return criteria.list();

    }

    public List listWithGrouping(Class cls, String groupProperty, WhereStatement... wheres) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //Add where clause
        for (WhereStatement where : wheres) {
            //set operator
            switch (where.getOperator()) {
                case "=":
                    criteria.add(Restrictions.eq(where.getProperty(), where.getValue()));
                    break;
                case "like":
                    criteria.add(Restrictions.like(where.getProperty(), where.getValue()));
                    break;
                case "<>":
                    criteria.add(Restrictions.ne(where.getProperty(), where.getValue()));
                    break;
                case "IS NULL":
                    criteria.add(Restrictions.isNull(where.getProperty()));
                    break;
                case "IS NOT NULL":
                    criteria.add(Restrictions.isNotNull(where.getProperty()));
                    break;
                case "less_than":
                    criteria.add(Restrictions.lt(where.getProperty(), where.getValue()));
                    break;

                case "greater_than":
                    criteria.add(Restrictions.gt(where.getProperty(), where.getValue()));
                    break;

            }
        }

        //add group property       
        criteria.setProjection(Projections.groupProperty(groupProperty));

        //return list
        return criteria.list();

    }

    //list by multiple wheres using AND
    public List search(Class cls, List<WhereStatement> wheres) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //Add where clause
        for (WhereStatement where : wheres) {
            //set operator
            switch (where.getOperator()) {
                case "=":
                    criteria.add(Restrictions.eq(where.getProperty(), where.getValue()));
                    break;
                    
                case "like":
                    criteria.add(Restrictions.like(where.getProperty(), where.getValue()));
                    break;
                    
                case "<>":
                    criteria.add(Restrictions.ne(where.getProperty(), where.getValue()));
                    break;
                    
                case "IS NULL":
                    criteria.add(Restrictions.isNull(where.getProperty()));
                    break;
                    
                case "IS NOT NULL":
                    criteria.add(Restrictions.isNotNull(where.getProperty()));
                    break;
                    
                case "less_than":
                    criteria.add(Restrictions.lt(where.getProperty(), where.getValue()));
                    break;

                case "greater_than":
                    criteria.add(Restrictions.gt(where.getProperty(), where.getValue()));
                    break;

            }
        }

        //return list
        return criteria.list();

    }

    //list by multiple wheres using AND
    public List search(Class cls, List<WhereStatement> wheres, String orderProperty, String orderType) {
        //get current session using session factory
        Session session = sessionFactory.getCurrentSession();
        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //Add where clause
        for (WhereStatement where : wheres) {
            //set operator
            switch (where.getOperator()) {
                case "=":
                    criteria.add(Restrictions.eq(where.getProperty(), where.getValue()));
                    break;
                case "like":
                    criteria.add(Restrictions.like(where.getProperty(), where.getValue()));
                    break;
                case "<>":
                    criteria.add(Restrictions.ne(where.getProperty(), where.getValue()));
                    break;
                case "IS NULL":
                    criteria.add(Restrictions.isNull(where.getProperty()));
                    break;
                case "IS NOT NULL":
                    criteria.add(Restrictions.isNotNull(where.getProperty()));
                    break;
                case "less_than":
                    criteria.add(Restrictions.lt(where.getProperty(), where.getValue()));
                    break;

                case "greater_than":
                    criteria.add(Restrictions.gt(where.getProperty(), where.getValue()));
                    break;

            }
        }
        //set order by
        if (orderType.equals(SystemVarList.ASC)) {
            criteria.addOrder(Order.asc(orderProperty));
        } else {
            criteria.addOrder(Order.desc(orderProperty));
        }

        //return list
        return criteria.list();

    }

//    //list for select box
//    public List<SelectBox> loadSelectBox(Class cls, String codeProperty, String textProperty, String status) {
//        //get current session using session factory
//        List list;
//        Session session = sessionFactory.getCurrentSession();
//      
//        Criteria criteria = session.createCriteria(cls); 
//         if (!status.equals(SystemVarList.ALL)) {//check is select all records
//            //check only status match records
//            criteria.add(Restrictions.eq("dlbStatus.statusCode", status));
//         }
//        list=criteria.list();
//        return list;
//
//    }
    //list for select box
    public List loadSelectBox(Class cls, String codeProperty, String textProperty, WhereStatement... wheres) {
        //get current session using session factory
        List list;
        Session session = sessionFactory.getCurrentSession();

        //create criteria
        Criteria criteria = session.createCriteria(cls);

        //Add where clause
        for (WhereStatement where : wheres) {
            criteria.add(Restrictions.eq(where.getProperty(), where.getValue()));
        }
        list = criteria.list();
        return list;

    }


