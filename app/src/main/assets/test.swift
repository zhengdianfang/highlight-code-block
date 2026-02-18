import UIKit

// Swift Language Highlight Test

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        let message = "Hello, Swift!"
        print("Message: \(message)")
        
        let number = 42
        let pi = 3.14159
        
        if number > 10 {
            print("Number is greater than 10")
        } else {
            print("Number is 10 or less")
        }
        
        return true
    }
}

struct Person {
    var name: String
    var age: Int
}

enum Direction {
    case north, south, east, west
}

extension String {
    func reversed() -> String {
        return String(self.reversed())
    }
}
