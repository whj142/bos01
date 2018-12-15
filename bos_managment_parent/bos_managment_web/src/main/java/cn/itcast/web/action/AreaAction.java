/**
 * 
 */
package cn.itcast.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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

import cn.itcast.bos.domain.base.Area;
import cn.whj.service.AreaService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author wuhuijie 2018年12月11日
 * 
 */
@ParentPackage("struts-default")
@Namespace("/")
@Actions
@Controller
@Scope("prototype")
public class AreaAction extends ActionSupport implements ModelDriven<Area>  {

	private Area model = new Area();

	@Override
	public Area getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	@Autowired
	private AreaService areaService;

	private File areaFile;

	/**
	 * @param areaFile the areaFile to set
	 */
	public void setAreaFile(File areaFile) {
		this.areaFile = areaFile;
	}

	@SuppressWarnings("resource")
	@Action(value = "areaAction_importXls")
	public String importXls() throws IOException {
		String flag = "1";

		try {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(areaFile));
			// 读取sheet
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
			List<Area> list = new ArrayList<Area>();
			for (Row row : sheet) {
				// 得到总行数
				int rowsize = sheet.getLastRowNum();
				// 获取总列数
				int colNum = row.getLastCellNum();
				// 获取第一行，跳过标题栏
				int rowNum = row.getRowNum();
				if (rowNum == 0) {
					continue;
				}
				// 将读取到的数据封装到区域对象
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				Area area = new Area();
				area.setId(id);
				area.setProvince(province);
				area.setCity(city);
				area.setDistrict(district);
				area.setPostcode(postcode);
				list.add(area);
			}
			areaService.batchSave(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;

	}

	private int page;
	private int rows;

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		this.rows = rows;
	}

//	,results= {@Result(name="success", location = "/pages/base/area.jsp")}
	@Action(value = "areaAction_pageQuery")
	public String pageQuery() throws IOException {
		Pageable pageable = new PageRequest(page - 1, rows);
		Specification<Area> spec = new Specification<Area>() {

			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				if (StringUtils.isNotBlank(model.getProvince())) {
					String province = model.getProvince();
					list.add(cb.like(root.get("province").as(String.class), "%" + province + "%"));
				}
				if (StringUtils.isNotBlank(model.getCity())) {
					String city = model.getCity();
					list.add(cb.like(root.get("city").as(String.class), "%" + city + "%"));
				}
				if (StringUtils.isNotBlank(model.getDistrict())) {
					String district = model.getDistrict();
					list.add(cb.like(root.get("district").as(String.class), "%" + district + "%"));
				}
				Predicate[] predicateArr = new Predicate[list.size()];

				return query.where(list.toArray(predicateArr)).getRestriction();
			}
		};
		// 执行分页查询
		Page<Area> page = areaService.pageQuery(spec, pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());

		JsonConfig jsonConfig = new JsonConfig();
		String[] excludes = { "subareas" };
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}
	
	@Action(value="areaAction_findAll")
	public String findAll() throws IOException {
		List<Area> list = areaService.findAll();
		JsonConfig jsonConfig = new JsonConfig();
		String[] excludes = {"subareas"};
		jsonConfig.setExcludes(excludes);
		JSONArray fromObject = JSONArray.fromObject(list, jsonConfig);
		String json = fromObject.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}
}
