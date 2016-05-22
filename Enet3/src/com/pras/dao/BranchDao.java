package com.pras.dao;

import java.util.Collection;

import com.pras.dto.BranchDto;
import com.pras.model.Branch;

public interface BranchDao {

	public void addBranch(BranchDto branch);
	public void removeBranch(long id);
	public void editBranch(long id, BranchDto branch);
	public Collection<? extends BranchDto> getBranches();
	public BranchDto getBranch(long id);
	
}
