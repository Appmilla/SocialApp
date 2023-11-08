//
//  StoreTabView.swift
//  iosApp
//
//  Created by Richard Woollcott on 31/10/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct StoreTabView: View {
    let greet = Greeting().greet()

        var body: some View {
            Text(greet)
        }
}

#Preview {
    StoreTabView()
}
