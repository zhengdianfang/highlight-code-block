package main

import "fmt"

type Shape interface {
    Area() float64
}

type Rectangle struct {
    width  float64
    height float64
}

func (r Rectangle) Area() float64 {
    return r.width * r.height
}

func main() {
    rect := Rectangle{width: 10, height: 5}
    fmt.Printf("Area: %f\n", rect.Area())

    // Loop
    for i := 0; i < 5; i++ {
        if i%2 == 0 {
            fmt.Println(i, "is even")
        } else {
            fmt.Println(i, "is odd")
        }
    }

    /*
       Multi-line comment
    */
    go func() {
        fmt.Println("Running in a goroutine")
    }()
}
