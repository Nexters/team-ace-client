//
//  KeyChainManger.swift
//  iosApp
//
//  Created by 최지철 on 8/14/25.
//

import Foundation
import Security

enum Keychain {
    static func read(_ key: String) -> String? {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: "com.nexters.emotia.device",
            kSecAttrAccount as String: key,
            kSecReturnData as String: true,
            kSecMatchLimit as String: kSecMatchLimitOne
        ]
        var item: CFTypeRef?
        let status = SecItemCopyMatching(query as CFDictionary, &item)
        guard status == errSecSuccess, let data = item as? Data else {
            return nil
        }
        return String(data: data, encoding: .utf8)
    }

    static func write(_ key: String, _ value: String) {
        let data = value.data(using: .utf8)!
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrService as String: "com.nexters.emotia.device",
            kSecAttrAccount as String: key
        ]
        let attrs: [String: Any] = [kSecValueData as String: data]
        let status = SecItemAdd(query.merging(attrs) {
            $1
        } as CFDictionary, nil)
        if status == errSecDuplicateItem {
            SecItemUpdate(query as CFDictionary, [kSecValueData as String: data] as CFDictionary)
        }
    }
}

func loadOrCreateDeviceUUID() -> String {
    let key = "device_uuid"
    if let existing = Keychain.read(key), !existing.isEmpty {
        return existing
    }
    let uuid = UUID().uuidString
    Keychain.write(key, uuid)
    return uuid
}
