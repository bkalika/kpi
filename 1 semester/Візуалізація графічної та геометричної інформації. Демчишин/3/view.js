var scene = new THREE.Scene();
        scene.background = new THREE.Color(0x008000);
        const camera = new THREE.PerspectiveCamera(20, 2, 0.1, 1000);
        var renderer = new THREE.WebGLRenderer();
        renderer.setSize(window.innerWidth, window.innerHeight);
        document.body.appendChild(renderer.domElement);

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