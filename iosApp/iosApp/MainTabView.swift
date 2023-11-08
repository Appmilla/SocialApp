//
//  MainTabView.swift
//  iosApp
//
//  Created by Richard Woollcott on 31/10/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import AmitySDK
import AmityUIKit

struct MainTabView: View {
    var body: some View {
        TabView {
            HomeTabView()
                .tabItem {
                    Image(systemName: "house")
                    Text("Home")
                }
            
            ChatTabView()
                    .tabItem {
                        Image(systemName: "message")
                        Text("Chat")
                    }
            
            StoreTabView()
                .tabItem {
                    Image(systemName: "cart")
                    Text("Store")
                }
            
            ClubsTabView()
                .tabItem {
                    Image(systemName: "person.3")
                    Text("Clubs")
                }
            
            MoreTabView() // Your More view
                    .tabItem {
                        Image(systemName: "ellipsis")
                        Text("More")
                    }
        }
    }
}

#Preview {
    MainTabView()
}
