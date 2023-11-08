//
//  CustomChannelEventHandler.swift
//  iosApp
//
//  Created by Richard Woollcott on 29/10/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

/*
import Foundation
import AmityUIKit

class CustomChannelEventHandler: AmityChannelEventHandler {
    
    override func channelDidTap(from source: AmityViewController,
                                channelId: String, subChannelId: String) {
        var settings = AmityMessageListViewController.Settings()
        settings.shouldShowChatSettingBarButton = true
        let viewController = AmityMessageListViewController.make(channelId: channelId, subChannelId: subChannelId, settings: settings)
        if ChatFeatureSetting.shared.iscustomMessageEnabled {
            viewController.dataSource = self
        } else {
            viewController.dataSource = nil
        }
        source.navigationController?.pushViewController(viewController, animated: true)
    }
    
}

extension CustomChannelEventHandler: AmityMessageListDataSource {
    func cellForMessageTypes() -> [AmityMessageTypes : AmityMessageCellProtocol.Type] {
        return [
            .textIncoming: CustomMessageTextIncomingTableViewCell.self
        ]
    }
}*/
