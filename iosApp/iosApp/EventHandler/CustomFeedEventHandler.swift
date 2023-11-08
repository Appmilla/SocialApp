//
//  CustomFeedEventHandler.swift
//  iosApp
//
//  Created by Richard Woollcott on 29/10/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import AmityUIKit

class CustomFeedEventHandler: AmityFeedEventHandler {
    override func sharePostDidTap(from source: AmityViewController, postId: String) {
        let urlString = "https://amity.co/posts/\(postId)"
        guard let url = URL(string: urlString) else { return }
        let viewController = AmityActivityController.make(activityItems: [url])
        source.present(viewController, animated: true, completion: nil)
    }
    
    override func sharePostToGroupDidTap(from source: AmityViewController, postId: String) {
    }
    
    override func sharePostToMyTimelineDidTap(from source: AmityViewController, postId: String) {
    }
}
