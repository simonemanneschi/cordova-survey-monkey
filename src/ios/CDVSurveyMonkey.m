/*
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

#import "CDVSurveyMonkey.h"

@implementation CDVSurveyMonkey

CDVPluginResult* pluginResult = nil;
CDVInvokedUrlCommand* thisCommand = nil;
- (void)showSurvey:(CDVInvokedUrlCommand*)command
{
    thisCommand = command;
    NSString* surveyHash = [command.arguments objectAtIndex:0];
    if(sizeof(command.arguments)>1)
    {
        NSString* surveyVariables = [command.arguments objectAtIndex:1];
        NSError *jsonError;
        NSData *objectData = [surveyVariables dataUsingEncoding:NSUTF8StringEncoding];
        NSDictionary *json = [NSJSONSerialization JSONObjectWithData:objectData
                                              options:NSJSONReadingMutableContainers
                                                error:&jsonError];
        
        _feedbackController = [[SMFeedbackViewController alloc] initWithSurvey:surveyHash andCustomVariables:json];
    }
    else
    {
        _feedbackController = [[SMFeedbackViewController alloc] initWithSurvey:surveyHash];
    }
    
    _feedbackController.delegate = self;
    
    [_feedbackController presentFromViewController:self.viewController animated:YES completion:nil];
}


- (void)respondentDidEndSurvey:(SMRespondent *)respondent error:(NSError *)error {
    NSLog(@"Received response");
    if (respondent != nil) {
        NSDictionary *jsDict = [respondent toJson];
        NSData *jsonData2 = [NSJSONSerialization dataWithJSONObject:jsDict    options:NSJSONWritingPrettyPrinted error:&error];
        NSString *jsonString = [[NSString alloc] initWithData:jsonData2 encoding:NSUTF8StringEncoding];
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:jsonString];
    }
    else {
      NSLog(@"%@", error.description);
        pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
    }
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:thisCommand.callbackId];
}

@end
