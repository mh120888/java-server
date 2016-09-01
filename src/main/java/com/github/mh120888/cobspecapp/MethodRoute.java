package com.github.mh120888.cobspecapp;

public class MethodRoute {
    private String method;
    private String path;

    public MethodRoute(String method, String path) {
        this.method = method;
        this.path = path;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash += method.hashCode() + path.hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MethodRoute other = (MethodRoute) obj;
        if (!method.equals(other.method))
            return false;
        if (!path.equals(other.path))
            return false;
        return true;
    }
}
