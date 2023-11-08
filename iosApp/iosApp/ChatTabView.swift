//
//  ChatTabView.swift
//  iosApp
//
//  Created by Richard Woollcott on 31/10/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import UIKit
import AmityUIKit

struct ChatTabView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        //ChatFeatureSetting.shared.iscustomMessageEnabled = false
        let chatViewController = AmityRecentChatViewController.make(channelType: .community)
        let navigationController = UINavigationController(rootViewController: chatViewController)
       
        return navigationController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // Update the view controller if needed
    }
}

#Preview {
    ChatTabView()
}

