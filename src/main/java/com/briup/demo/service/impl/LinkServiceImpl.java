package com.briup.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.Link;
import com.briup.demo.bean.LinkExample;
import com.briup.demo.bean.LinkExample.Criteria;
import com.briup.demo.mapper.LinkMapper;
import com.briup.demo.service.ILingkService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;

/**
 * 操作链接的service功能类
 * 
 * @author 19576
 *
 */
@Service
public class LinkServiceImpl implements ILingkService {
	@Autowired
	private LinkMapper linkMapper;

	@Override
	public void saveOrUpdateLink(Link link) throws CustomerException {
		// 参数为引用类型，要做判空处理
		if (link == null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "参数为空");
		}
		// 判断link对象的id是否为空，如果为空则新增链接，如果不为空则修改链接
		if (link.getId() == null) {
			linkMapper.insert(link);
		} else {
			linkMapper.updateByPrimaryKey(link);
		}
	}

	@Override
	public List<Link> fingAllLinks() throws CustomerException {
//		LinkExample example = new LinkExample();
//		List<Link> list = linkMapper.selectByExample(example);
		return linkMapper.selectByExample(new LinkExample());
	}

	@Override
	public void deleteLinkById(int id) throws CustomerException {
		linkMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Link> finLinksByName(String name) throws CustomerException {
		LinkExample example = new LinkExample();
		name = name == null ? "" : name.trim();
		if ("".equals(name)) {
			//如果搜索条件没写，则返回所有数据
			return linkMapper.selectByExample(example);
		}else {
			//如果搜索条件不为null，则返回满足条件的数据
			Criteria criteria = example.createCriteria();
			criteria.andNameLike("%"+name+"%");
			return linkMapper.selectByExample(example);
		}
	}

}
