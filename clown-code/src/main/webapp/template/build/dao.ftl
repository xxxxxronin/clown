package ${config.packageNameDao};

import ${config.packageNameModel}.${tableModelName?cap_first}Model;
import ${config.packageNameModel}.${tableModelName?cap_first}Model;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
* Created by ${config.authorName} on 2016/7/4.
*
* @Author ${config.authorName}
* @Date 2016/7/4
*/
public interface ${table.tableModelName?cap_first}Dao {

    public List<${table.tableModelName?cap_first}> find${table.tableModelName?cap_first}List(@Param("query") ${table.tableModelName?cap_first}Model ${table.tableModelName}Model) throws Exception;

    public ${table.tableModelName?cap_first}Model find${table.tableModelName?cap_first}One(@Param("query") ${table.tableModelName?cap_first}Model ${table.tableModelName}Model) throws Exception;

    public int delete${table.tableModelName?cap_first}Batch(List<Integer> iids) throws Exception;

    public int update${table.tableModelName?cap_first}One(@Param("query") ${table.tableModelName?cap_first}Model ${table.tableModelName}Model) throws Exception;

    public int insert${table.tableModelName?cap_first}One(@Param("query") ${table.tableModelName?cap_first}Model ${table.tableModelName}Model) throws Exception;

}
