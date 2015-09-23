package com.github.cg.example.jsf.controller.frm;

import com.github.cg.example.core.dao.DAO;
import com.github.cg.example.core.manager.Manager;
import com.github.cg.example.core.model.IBaseEntity;


public abstract class SubFrmExternalPage<FE extends Frm<? extends Manager<? extends DAO<E,?>,E,?>,E,?>, E extends IBaseEntity<?>, 
											  MA extends Manager<? extends DAO<A,?>,A,?>, A extends IBaseEntity<?>> extends SubFrmBase<FE,E,MA,A> implements SubFrm {
				
	public SubFrmExternalPage(FE frmEntity, MA associationManager, String componentToUpdateId) {
		super(frmEntity, associationManager, componentToUpdateId);
	}
}