<?php
/**
 * PHP Syntax Highlighting Test
 */
namespace App\Controller;

use App\Model\User;
use Illuminate\Http\Request;

abstract class BaseController {
    protected $service;
    
    public function __construct(Service $service) {
        $this->service = $service;
    }
}

interface Renderable {
    public function render();
}

trait Logger {
    public function log($msg) {
        echo "[LOG] " . $msg;
    }
}

class UserController extends BaseController implements Renderable {
    use Logger;
    
    const MAX_USERS = 100;
    public static $status = 'active';
    private $users = [];

    public function index(Request $request) {
        $id = $request->input('id');
        
        if ($id) {
            return $this->getUser($id);
        }
        
        $list = [1, 2, 3, 4, 5];
        foreach ($list as $item) {
            $this->log("Processing item: $item");
        }
        
        return view('user.index', ['users' => $this->users]);
    }
    
    private function getUser($id) {
        // SQL query simulation
        $sql = "SELECT * FROM users WHERE id = ?";
        
        try {
            $user = new User($id);
            if ($user->isValid()) {
                return $user;
            } else {
                throw new \Exception("Invalid user");
            }
        } catch (\Exception $e) {
            return null;
        }
    }

    public function render() {
        echo "Rendering user controller";
    }
}

// Global function
function main() {
    $controller = new UserController(new Service());
    $controller->index(new Request());
    
    echo UserController::MAX_USERS;
}

main();
?>
