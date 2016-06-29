package com.pras.daoimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.pras.constant.Constants;
import com.pras.dao.BranchDao;
import com.pras.dto.BranchDetailsDto;
import com.pras.dto.BranchDto;
import com.pras.dto.UserDto;
import com.pras.dtohelper.BranchDtoHelper;
import com.pras.dtohelper.UserDtoHelper;
import com.pras.model.Branch;
import com.pras.model.User;
import com.pras.util.HibernateUtil;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;

public class BranchDaoImpl implements BranchDao{

	@Override
	public void addBranch(BranchDto branch) {
		// TODO Auto-generated method stub
		//Get Session
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        //start transaction
        session.beginTransaction();
        Branch b = BranchDtoHelper.getEntityFromDto(branch);
        User u = (User) session.get(User.class, branch.getBranchmanagerId());
        b.setBranchmanager(u);
        b.setStatus(Constants.CREATED);
        //Save the Model object
        session.save(b);
        session.getTransaction().commit();
        session.close();
	}

	@Override
	public void removeBranch(long id) {
		// TODO Auto-generated method stub
		//Get Session
        Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        //start transaction
        session.beginTransaction();
        Branch branch = (Branch) session.get(Branch.class, id);
        branch.setStatus(Constants.DELETED);
        session.saveOrUpdate(branch);
        session.getTransaction().commit();
        session.close();
	}

	@Override
	public void editBranch(long id, BranchDto branch) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionAnnotationFactory().openSession();
        //start transaction
        session.beginTransaction();
        
        Branch b1 = (Branch) session.get(Branch.class, id);
        Branch b = BranchDtoHelper.getEntityFromDto(branch);
        User u = (User) session.get(User.class, branch.getBranchmanagerId());
        
        b1.setAddress(branch.getAddress());
        b1.setContact(branch.getContact());
        b1.setPincode(branch.getPincode());
        b1.setPlace(branch.getPlace());
        b1.setBranchmanager(u);
        b1.setStatus(branch.getStatus());
        //Save the Model object
        session.saveOrUpdate(b1);
        session.getTransaction().commit();
        session.close();
	}

	@Override
	public Collection<? extends BranchDto> getBranches() {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Branch.class);
		cr.add(Restrictions.eq("status", Constants.CREATED)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) ;
		List<Branch> branches = cr.list();
		List<BranchDto> dtos = new ArrayList<BranchDto>();
		BranchDto dto = null;
		for(int i=0;i<branches.size();i++) {
			dto = BranchDtoHelper.getDtoFromEntity(branches.get(i));
			dto.setId(branches.get(i).getId());
			dtos.add(dto);
		}
		session.close();
		return dtos;
	}

	@Override
	public BranchDto getBranch(long id) {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		Branch b = (Branch) session.get(Branch.class, id);
		BranchDto dto = BranchDtoHelper.getDtoFromEntity(b);
		dto.setId(id);
		session.close();
		return dto;
	}

	@Override
	public BranchDetailsDto getBranchDetails(long id) {
		Session session = HibernateUtil.getSessionAnnotationFactory()
				.openSession();
		session.beginTransaction();
		Branch b = (Branch) session.get(Branch.class, id);
		BranchDetailsDto dto = BranchDtoHelper.getDetailsDtoFromEntity(b);
		return dto;
	}

}
