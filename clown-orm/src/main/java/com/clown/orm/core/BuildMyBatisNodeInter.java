package com.clown.orm.core;

import org.apache.ibatis.scripting.xmltags.SqlNode;

/**
 * Created by len.li on 21/3/2016.
 */
public interface BuildMyBatisNodeInter {

    public SqlNode build() throws Exception;
}
