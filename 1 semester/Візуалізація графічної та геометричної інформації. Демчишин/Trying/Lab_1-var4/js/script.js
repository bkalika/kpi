var renderer,
    scene,
   	camera,
   	myCanvas = document.getElementById('myCanvas');


// RENDERER
renderer = new THREE.WebGLRenderer({canvas: myCanvas, antialias: true});
renderer.setClearColor(0x000000);
renderer.setPixelRatio(window.devicePixelRatio);
renderer.setSize(window.innerWidth, window.innerHeight);


// CAMERA
camera = new THREE.PerspectiveCamera(35, window.innerWidth / window.innerHeight, 0.1, 3000);


// SCENE
scene = new THREE.Scene();


// LIGHTS
var light = new THREE.AmbientLight(0xffffff);
scene.add(light);


// function Cylindrical Equal-Area projection
function cylindrEqArProj(u, v) {

    u *= 4 * Math.PI / 9;   // l = 80
    v *= Math.PI / 6;       // phi = 30

    var R = 1;
    var l0 = Math.PI / 2.4;     // 75
    var phi0 = Math.PI / 6;     // 30 

    var x = R * (u - l0) * Math.cos(phi0);
    var y = R * Math.sin(v) / Math.cos(phi0);
    var z = 1;

    return new THREE.Vector3(x, y, z);
}; 

// MATERIAL
var geometry = new THREE.ParametricGeometry(cylindrEqArProj, 10, 10); 
var wireframe = new THREE.WireframeGeometry( geometry );
var map = new THREE.LineSegments( wireframe );
    map.material.opacity = 0.5;
scene.add( map );


// POSITION CAMERAS AND MESHES
map.position.x += 0.5;
map.position.y -= 0.25;
camera.position.z += 2.75;


//RENDER
render();

function render() {
	// map.rotation.x += 0.01;
 	// map.rotation.y += 0.01;
    renderer.render(scene, camera);
  	requestAnimationFrame(render);
}