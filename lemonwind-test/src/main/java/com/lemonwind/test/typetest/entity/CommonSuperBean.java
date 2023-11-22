package com.lemonwind.test.typetest.entity;

import java.util.List;

public class CommonSuperBean<JoSon> extends CommonEntity<JoSon> {

    List<? super JoSon> superBound;
    List<? extends JoFather> extendsBound;

    public CommonSuperBean() {
    }

	public CommonSuperBean(List<? super JoSon> superBound, List<? extends JoFather> extendsBound) {
		super();
		this.superBound = superBound;
		this.extendsBound = extendsBound;
	}

	public List<? super JoSon> getSuperBound() {
		return superBound;
	}

	public void setSuperBound(List<? super JoSon> superBound) {
		this.superBound = superBound;
	}

	public List<? extends JoFather> getExtendsBound() {
		return extendsBound;
	}

	public void setExtendsBound(List<? extends JoFather> extendsBound) {
		this.extendsBound = extendsBound;
	}
	
}
