//
//  HomeTabView.swift
//  iosApp
//
//  Created by Richard Woollcott on 31/10/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import UIKit
import AmityUIKit

struct HomeTabView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let homepageViewController = AmityCommunityHomePageViewController.make()
        let navigationController = UINavigationController(rootViewController: homepageViewController)
        return navigationController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // Update the view controller if needed
    }
}

#Preview {
    HomeTabView()
}
