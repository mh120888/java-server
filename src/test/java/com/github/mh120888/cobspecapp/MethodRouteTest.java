package com.github.mh120888.cobspecapp;

import org.junit.Test;

import static org.junit.Assert.*;

public class MethodRouteTest {
    @Test
    public void methodRoutesWithTheSameAttributesAreEqual() {
        MethodRoute methodRouteA = new MethodRoute("GET", "/path");
        MethodRoute methodRouteB = new MethodRoute("GET", "/path");

        assertTrue(methodRouteA.equals(methodRouteB));
    }

    @Test
    public void methodRoutesWithDifferentMethodsAreNotEqual() {
        MethodRoute methodRouteA = new MethodRoute("POST", "/path");
        MethodRoute methodRouteB = new MethodRoute("GET", "/path");

        assertFalse(methodRouteA.equals(methodRouteB));
    }

    @Test
    public void methodRoutesWithDifferentPathsAreNotEqual() {
        MethodRoute methodRouteA = new MethodRoute("GET", "/path");
        MethodRoute methodRouteB = new MethodRoute("GET", "/path-that-is-different");

        assertFalse(methodRouteA.equals(methodRouteB));
    }

    @Test
    public void equalObjectsReturnsSameHashCode() {
        MethodRoute methodRouteA = new MethodRoute("GET", "/path");
        MethodRoute methodRouteB = new MethodRoute("GET", "/path");

        assertTrue(methodRouteA.hashCode() == methodRouteB.hashCode());
    }

    @Test
    public void unequalObjectsReturnDifferentHashCodes() {
        MethodRoute methodRouteA = new MethodRoute("GET", "/path");
        MethodRoute methodRouteB = new MethodRoute("GET", "/path-that-is-different");

        assertFalse(methodRouteA.hashCode() == methodRouteB.hashCode());
    }

    @Test
    public void objectWithBlankMethodIsEqualToObjectWithAnyMethodAndSamePath() {
        MethodRoute methodRouteA = new MethodRoute("", "/path");
        MethodRoute methodRouteB = new MethodRoute("GET", "/path");

        assertFalse(methodRouteA.equals(methodRouteB));
    }
}