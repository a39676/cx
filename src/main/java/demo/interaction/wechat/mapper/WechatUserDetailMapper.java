package demo.interaction.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import demo.interaction.wechat.pojo.po.WechatUserDetail;
import demo.interaction.wechat.pojo.po.WechatUserDetailExample;

public interface WechatUserDetailMapper {
	long countByExample(WechatUserDetailExample example);

	int deleteByExample(WechatUserDetailExample example);

	int deleteByPrimaryKey(Long id);

	int insert(WechatUserDetail row);

	int insertSelective(WechatUserDetail row);

	List<WechatUserDetail> selectByExampleWithRowbounds(WechatUserDetailExample example, RowBounds rowBounds);

	List<WechatUserDetail> selectByExample(WechatUserDetailExample example);

	WechatUserDetail selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("row") WechatUserDetail row, @Param("example") WechatUserDetailExample example);

	int updateByExample(@Param("row") WechatUserDetail row, @Param("example") WechatUserDetailExample example);

	int updateByPrimaryKeySelective(WechatUserDetail row);

	int updateByPrimaryKey(WechatUserDetail row);

	List<String> findOpenIdList(@Param("index") Long index, @Param("limit") Integer limit);
}