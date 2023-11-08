//
//  AppDelegate.swift
//  iosApp
//
//  Created by Richard Woollcott on 29/10/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import UIKit
import AmitySDK
import AmityUIKit
import Firebase

//@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
  
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Perform any setup required before the application launches.
        // For example, initialize any SDKs, services, etc.
        
        // Setup AmityUIKit
        AppManager.shared.setupAmityUIKit()
        
        //AppManager.shared.register(withUserId: "Rich Woollcott") // simulator iPhone 15 Pro
        AppManager.shared.register(withUserId: "James Guest") // real device
        //AppManager.shared.register(withUserId: "Rich Demo") // real device
        
        //AppManager.shared.unregister()
        
        FirebaseApp.configure()
        
        return true
    }

    // MARK: UISceneSession Lifecycle
    
    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }
}

/*
class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        print("Your code here")
        
        // Setup AmityUIKit
        AppManager.shared.setupAmityUIKit()
        
        return true
    }
}
*/

class SampleSessionHandler: SessionHandler {
    
    func sessionWillRenewAccessToken(renewal: AccessTokenRenewal) {
        renewal.renew()
    }
    
}
