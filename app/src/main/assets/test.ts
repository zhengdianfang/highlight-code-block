// TypeScript sample
interface User {
    id: number;
    name: string;
    email?: string;
}

enum Direction {
    Up = "UP",
    Down = "DOWN",
    Left = "LEFT",
    Right = "RIGHT"
}

type Result<T> = {
    data: T;
    error: string | null;
};

@Injectable()
class UserService {
    private users: User[] = [];

    constructor(private readonly db: Database) {}

    async findById(id: number): Promise<User | undefined> {
        return this.users.find(u => u.id === id);
    }

    async create(name: string, email?: string): Promise<User> {
        const user: User = { id: this.users.length + 1, name, email };
        this.users.push(user);
        return user;
    }
}

function greet(user: User): string {
    return `Hello, ${user.name}!`;
}

const direction: Direction = Direction.Up;
const count = 42;
console.log(greet({ id: 1, name: "Alice" }));
