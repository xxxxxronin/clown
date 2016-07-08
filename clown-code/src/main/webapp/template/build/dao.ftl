package ${packageName}.dao;

import ${packageName}.model.${tableModelName?cap_first}Model;
import ${packageName}.model.${tableModelName?cap_first}Model;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
* Created by ${author?default("AutoClownCode")} on 2016/7/4.
*
* @Author ${author?default("AutoClownCode")}
* @Date 2016/7/4
*/
public interface ${tableModelName?cap_first}Dao {

    public List<${tableModelName?cap_first}> find${tableModelName?cap_first}List(@Param("query") ${tableModelName?cap_first}Model ${tableModelName}Model) throws Exception;

    public ${tableModelName?cap_first}Model find${tableModelName?cap_first}One(@Param("query") ${tableModelName?cap_first}Model ${tableModelName}Model) throws Exception;

    public int delete${tableModelName?cap_first}Batch(List<Integer> iids) throws Exception;

    public int update${tableModelName?cap_first}One(@Param("query") ${tableModelName?cap_first}Model ${tableModelName}Model) throws Exception;

    public int insert${tableModelName?cap_first}One(@Param("query") ${tableModelName?cap_first}Model ${tableModelName}Model) throws Exception;

}
