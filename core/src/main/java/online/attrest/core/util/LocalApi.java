package online.attrest.core.util;

import java.util.Date;

public class LocalApi 
{
    private String route;
    private String comment;
    private String action;
    private String params;

    //API是否一致
    private boolean exist;
    private String compareresult;

    public String getCompareresult() {
        return compareresult;
    }

    public void setCompareresult(String compareresult) {
        this.compareresult = compareresult;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getParams() {
        return params;
    }
    public void setParams(String params) {
        this.params = params;
    }
    public String getRoute() {
        return route;
    }
    public void setRoute(String route) {
        this.route = route;
    }

	public String toWarring() {
		return (new Date())+" [AttRest]->需要实现接口:"+ getComment()+ ";Controller:" + getRoute() + ",Action:"+getAction() + ",Params:"+getParams();
	}

}