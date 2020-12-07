package ac.hurley.springboot.dao;

import ac.hurley.springboot.model.LearnResource;
import ac.hurley.util.StringUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface LearnMapper {

    @Insert("insert into learn_resource(author,title,url) values(#{author},#{title},#{url}")
    int add(LearnResource learnResource);

    @Update("update learn_resource set author=#{author},title=#{title},url=#{url} where id=#{id}")
    int update(LearnResource learnResource);

    @DeleteProvider(type = LearnSqlBuilder.class, method = "deleteByids")
    int deleteByIds(@Param("ids") String[] ids);

    /**
     * 根据id查询的接口
     *
     * @param id
     * @return
     */
    @Select("select * from learn_resource where id=#{id}")
    @Results(id = "learnMap", value = {
            @Result(column = "id", property = "id", javaType = Long.class),
            @Result(property = "author", column = "author", javaType = String.class),
            @Result(property = "title", column = "title", javaType = String.class)
    })
    LearnResource queryLearnResourceById(@Param("id") Long id);

    @SelectProvider(type = LearnSqlBuilder.class, method = "queryLearnResourceByParams")
    List<LearnResource> queryLearnResourceList(Map<String, Object> params);

    class LearnSqlBuilder {
        /**
         * 根据参数查询
         *
         * @param params
         * @return
         */
        public String queryLearnResourceByParams(final Map<String, Object> params) {
            StringBuffer sql = new StringBuffer();
            sql.append("select * from learn_resource where 1=1");
            if (!StringUtil.isNull((String) params.get("author"))) {
                sql.append(" and author like %").append((String) params.get("author")).append("%'");
            }
            if (!StringUtil.isNull((String) params.get("title"))) {
                sql.append(" and title like %").append((String) params.get("title")).append("%'");
            }
            System.out.println("查询sql==" + sql.toString());
            return sql.toString();
        }

        /**
         * 根据id删除
         *
         * @param ids
         * @return
         */
        public String deleteByids(@Param("ids") final String[] ids) {
            StringBuffer sql = new StringBuffer();
            sql.append("delete from learn_resource where id in(");
            for (int i = 0; i < ids.length; i++) {
                if (i == ids.length - 1) {
                    sql.append(ids[i]);
                } else {
                    sql.append(ids[i]).append(",");
                }
            }
            sql.append(")");
            return sql.toString();
        }
    }
}
