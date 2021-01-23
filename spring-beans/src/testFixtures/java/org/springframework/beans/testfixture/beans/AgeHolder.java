package org.springframework.beans.testfixture.beans;

public interface AgeHolder {

	default int age() {
		return getAge();
	}

	int getAge();

	void setAge(int age);

}
