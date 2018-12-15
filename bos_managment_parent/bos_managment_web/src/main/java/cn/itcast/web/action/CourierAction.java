package cn.itcast.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.Standard;
import cn.whj.service.CourierService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

	private Courier model = new Courier();

	@Override
	public Courier getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	@Autowired
	private CourierService courierService;

	@Action(value = "courierAction_add", results = { @Result(name = "success", location = "/pages/base/courier.jsp") })
	public String add() {
		courierService.addCourier(model);
		return "success";
	}

	private int page;
	private int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	@Action(value = "courierAction_findAll")
	public String findAll() throws Exception {
		Pageable pageable = new PageRequest(page - 1, rows);

		Specification<Courier> spec = new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<Predicate>();
				String courierNum = model.getCourierNum();
				if (StringUtils.isNotBlank(courierNum)) {
					list.add(cb.equal(root.get("courierNum").as(String.class), courierNum));
				}
				String company = model.getCompany();
				if (StringUtils.isNotBlank(company)) {
					list.add(cb.equal(root.get("company").as(String.class), company));
				}
				String type = model.getType();
				if (StringUtils.isNotBlank(type)) {
					list.add(cb.equal(root.get("type").as(String.class), type));
				}
				Standard standard = model.getStandard();
				if (standard != null) {
					String name = standard.getName();
					if (StringUtils.isNotBlank(name)) {
						Join<Object, Object> join = root.join("standard");
						list.add(cb.like(join.get("name").as(String.class), "%" + name + "%"));
					}
				}
				String deltag = "0";
				list.add(cb.equal(root.get("deltag").as(String.class), deltag));
				Predicate[] pre = new Predicate[list.size()];
				return query.where(list.toArray(pre)).getRestriction();
			}

		};

		Page<Courier> page = courierService.findAll(spec, pageable);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", page.getContent());
		map.put("total", page.getTotalElements());
		// 将其转化为json
		JsonConfig jsonConfig = new JsonConfig();
		String[] excludes = { "fixedAreas" };
		jsonConfig.setExcludes(excludes);
		JSONObject object = JSONObject.fromObject(map, jsonConfig);
		String json = object.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}

	private String ids;

	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 删除指定快递员
	 */
	@Action(value = "courierAction_delete", results = {
			@Result(name = "success", location = "/pages/base/courier.jsp", type = "redirect") })
	public String delete() {
		courierService.delete(ids);

		return "success";

	}
}
