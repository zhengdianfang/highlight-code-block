#include <iostream>
#include <vector>
#include <string>

// A simple C++ class example
class Greeter {
public:
    Greeter(const std::string& name) : name_(name) {}

    void sayHello() const {
        std::cout << "Hello, " << name_ << "!" << std::endl;
    }

private:
    std::string name_;
};

template <typename T>
T add(T a, T b) {
    return a + b;
}

int main() {
    // Vector usage
    std::vector<int> numbers = {1, 2, 3, 4, 5};
    
    for (const auto& num : numbers) {
        if (num % 2 == 0) {
            std::cout << num << " is even." << std::endl;
        } else {
            std::cout << num << " is odd." << std::endl;
        }
    }

    Greeter greeter("World");
    greeter.sayHello();

    auto result = add(10, 20);
    std::cout << "Result: " << result << std::endl;

    return 0;
}
