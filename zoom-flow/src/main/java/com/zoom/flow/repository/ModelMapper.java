package com.zoom.flow.repository;

import com.zoom.flow.entity.AbstractModel;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ModelMapper {

    @Insert("insert into ACT_DE_MODEL (id, name, model_key, description, model_comment, created, created_by, " +
            "last_updated, last_updated_by, version, model_editor_json, model_type, thumbnail, tenant_id) " +
            "values (#{id, jdbcType=VARCHAR}, #{name, jdbcType=VARCHAR}, #{key, jdbcType=VARCHAR}, " +
            "#{description, jdbcType=VARCHAR}, #{comment, jdbcType=VARCHAR}, #{created, jdbcType=TIMESTAMP}, " +
            "#{createdBy, jdbcType=VARCHAR}, #{lastUpdated, jdbcType=TIMESTAMP}, #{lastUpdatedBy, jdbcType=VARCHAR}, " +
            "#{version, jdbcType=INTEGER}, #{modelEditorJson, jdbcType=VARCHAR}, #{modelType, jdbcType=INTEGER}, " +
            "#{thumbnail, jdbcType=BLOB}, #{tenantId, jdbcType=VARCHAR})")
    void insert(AbstractModel model);

    @UpdateProvider(type = ModelMapperProvider.class, method = "updateById")
    void update(AbstractModel model);

    @Select("select * from ACT_DE_MODEL where id = #{id, jdbcType=VARCHAR}")
    @Results(id = "modelResult", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "model_key", property = "key"),
            @Result(column = "description ", property = "description"),
            @Result(column = "model_comment", property = "comment"),
            @Result(column = "created", property = "created", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "created_by", property = "createdBy"),
            @Result(column = "last_updated", property = "lastUpdated", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "last_updated_by", property = "lastUpdatedBy"),
            @Result(column = "version", property = "version", jdbcType = JdbcType.INTEGER),
            @Result(column = "model_editor_json", property = "modelEditorJson"),
            @Result(column = "model_type", property = "modelType", jdbcType = JdbcType.INTEGER),
            @Result(column = "thumbnail", property = "thumbnail", jdbcType = JdbcType.BLOB),
            @Result(column = "tenant_id", property = "tenantId"),})
    AbstractModel findById(@Param("id") String id);

    @Select("select * from ACT_DE_MODEL where model_key = #{key, jdbcType=VARCHAR} " +
            "and model_type = #{modelType, jdbcType=INTEGER} " +
            "order by version desc " +
            "limit 1")
    @ResultMap("modelResult")
    List<AbstractModel> findByKeyAndType(@Param("key") String key, @Param("modelType") Integer modelType);

    class ModelMapperProvider {
        public String updateById(AbstractModel model) {
            return new SQL() {{
                UPDATE("act_de_model");
                if (model.getName() != null)
                    SET("name = #{name, jdbcType=VARCHAR}");
                if (model.getKey() != null)
                    SET("model_key = #{key, jdbcType=VARCHAR}");
                if (model.getDescription() != null)
                    SET("description = #{description, jdbcType=VARCHAR}");
                if (model.getComment() != null)
                    SET("model_comment = #{comment, jdbcType=VARCHAR}");
                if (model.getCreated() != null)
                    SET("created = #{created, jdbcType=TIMESTAMP}");
                if (model.getCreatedBy() != null)
                    SET("created_by = #{createdBy, jdbcType=VARCHAR}");
                if (model.getLastUpdated() != null)
                    SET("last_updated = #{lastUpdated, jdbcType=TIMESTAMP}");
                if (model.getLastUpdatedBy() != null)
                    SET("last_updated_by = #{lastUpdatedBy, jdbcType=VARCHAR}");
                if (model.getVersion() > 0)
                    SET("version = #{version, jdbcType=INTEGER}");
                if (model.getModelEditorJson() != null)
                    SET("model_editor_json = #{modelEditorJson, jdbcType=VARCHAR}");
                if (model.getModelType() != null)
                    SET("model_type = #{modelType, jdbcType=INTEGER}");
                if (model.getThumbnail() != null)
                    SET("thumbnail = #{thumbnail, jdbcType=BLOB}");
                if (model.getTenantId() != null)
                    SET("tenant_id = #{tenantId, jdbcType=VARCHAR}");
                WHERE("id = #{id, jdbcType=VARCHAR}");
            }}.toString();
        }
    }
}
