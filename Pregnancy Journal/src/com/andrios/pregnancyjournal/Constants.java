/*
 * Copyright 2010-2011 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.andrios.pregnancyjournal;

public class Constants {
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This sample App is for demonstration purposes only.
    // It is not secure to embed your credentials into source code.
    // Please read the following article for getting credentials
    // to devices securely.
    // http://aws.amazon.com/articles/Mobile/4611615499399490
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//	public static final String ACCESS_KEY_ID = "AKIAIWYIRQCGAWMVVSCA";
//	public static final String SECRET_KEY = "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";	
//	
//
	public static final String ACCESS_KEY_ID = "AKIAIVB7RXLINULKTPUQ";
	public static final String SECRET_KEY = "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";	
	
	public static final String BABY_LOADING_BUCKET = "baby-loading";
	public static final String PROFILE_NAME = "Profile";
	public static final String BABY_NAME = "Baby-Name-List";
	public static final String CHECKLIST_NAME = "Baby-Coming-Checklist";
	public static final String CONTACTS_NAME = "Quick-Contacts";
	public static final String JOURNAL_NAME = "Journal";
	
	
	public static String getBabyLoadingBucket() {
		return (BABY_LOADING_BUCKET).toLowerCase();
	}
	
	public static String getBabyLoadingFile(String username, String password, String filename){
		return("users/"+username + password+"/" + filename).toLowerCase();
	}
	
}
