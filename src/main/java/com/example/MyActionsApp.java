/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.User;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements all intent handlers for this Action. Note that your App must extend from DialogflowApp
 * if using Dialogflow or ActionsSdkApp for ActionsSDK based Actions.
 */
public class MyActionsApp extends DialogflowApp {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyActionsApp.class);

  private static final String VALID_T_NUMBERS = "A123456,B123456,C123456,D123456,A234567,B234567,C234567,D234567,A132436,B132435,C132435,D132435";
  
  @ForIntent("Default Welcome Intent")
  public ActionResponse welcome(ActionRequest request) {
    LOGGER.info("Welcome intent start.");
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources");
    User user = request.getUser();

    if (user != null && user.getLastSeen() != null) {
      responseBuilder.add(rb.getString("welcome_back"));
    } else {
      responseBuilder.add(rb.getString("welcome"));
    }

    LOGGER.info("Welcome intent end.");
    return responseBuilder.build();
  }

  @ForIntent("bye")
  public ActionResponse bye(ActionRequest request) {
    LOGGER.info("Bye intent start.");
    ResponseBuilder responseBuilder = getResponseBuilder(request);
    ResourceBundle rb = ResourceBundle.getBundle("resources");

    responseBuilder.add(rb.getString("bye")).endConversation();
    LOGGER.info("Bye intent end.");
    return responseBuilder.build();
  }
  
  @ForIntent("fetch_auth_id")
  public ActionResponse fetchAuthId(ActionRequest request) {
	    LOGGER.info("Fetch Authentication ID intent start.");
	    ResponseBuilder responseBuilder = getResponseBuilder(request);
	    ResourceBundle rb = ResourceBundle.getBundle("resources");
	    String userAuthId = (String) request.getParameter("authid");
	    boolean found = false;
	    for(String authId : VALID_T_NUMBERS.split(",")) {
	    	if(authId.equalsIgnoreCase(userAuthId)) {
	    		found = true;
	    		break;
	    	}
	    }
	    if(found) {
	    	responseBuilder.add(rb.getString("fetch_auth_id_success").replace("$authid", userAuthId)).endConversation();
	    }else {
	    	responseBuilder.add(rb.getString("fetch_auth_id_failure").replace("$authid", userAuthId)).endConversation();
	    }
	    LOGGER.info("Fetch Authentication ID intent end.");
	    return responseBuilder.build();
	  }
}
