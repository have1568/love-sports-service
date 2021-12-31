package com.love.sports.utils;

import java.util.Collection;


public interface TreeModel {


    <T extends TreeModel> void setChildren(Collection<T> children);

    Number getSelfId();

    Number getSelfParentId();

    boolean getSelfRoot();

}
