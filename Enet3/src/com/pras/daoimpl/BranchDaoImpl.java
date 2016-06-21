package com.pras.daoimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;

import com.pras.dao.BranchDao;
import com.pras.dto.BranchDetailsDto;
import com.pras.dto.BranchDto;
import com.pras.dto.UserDto;
import com.pras.dtohelper.BranchDtoHelper;
import com.pras.dtohelper.UserDtoHelper;
import com.pras.model.Branch;
import com.pras.model.User;
import com.pras.util.HibernateUtil;

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
        session.delete(branch);
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
		List<Branch> branches = session.createCriteria(Branch.class).list();
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
