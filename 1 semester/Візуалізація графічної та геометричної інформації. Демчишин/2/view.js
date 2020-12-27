var scene = new THREE.Scene();
        var camera = new THREE.PerspectiveCamera(50, window.innerWidth / window.innerHeight, 1, 1000);
        camera.position.set(0, 0, 1);
        camera.lookAt(0, 0, 0);
    	var snailFunc = function (v, u, target) {
            
            a = 1;
            b = 1;
            
            u *= Math.PI;
            v *= 2 * Math.PI;
            u = u * 2;

            var x = u * Math.cos(v) * Math.sin(u);
            var y = u * Math.cos(u) * Math.cos(v);
            var z = -u * Math.sin(v);

            target.set(x, y, z).multiplyScalar(0.75);
        }