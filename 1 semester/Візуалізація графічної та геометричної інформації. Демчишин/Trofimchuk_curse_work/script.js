const CONFIG = {
  camera: {
    fov: 45,
    aspect: 1,
    near: 1,
    far: 1000,
  },
  colors: {
    background: 0x000000,
    figure: 0xd4af37,
  },
};

const form = document.forms.form;

let webGLRenderer;
let perspectiveCamera;
let scene;
let parametricGeometry;
let meshBasicMaterial;
let mesh;

let figureHeight;
let figureP;
let useWireframe;
const slices = 100;
const stacks = 100;
const cameraPositionZ = 3;

let orbitControls;

const tangentsAndNormalParameters = {
  u: 1 / 4,
  set setU(value) {
    this.u = value;
  },
  v: 1.5 / 2,
  set setV(value) {
    if (value < 0) {
      this.v = 0;
      return;
    }

    if (value > 1) {
      this.v = 1;
      return;
    }

    this.v = value;
  },
  vectorTu: null,
  arrowHelperTu: null,
  vectorTv: null,
  arrowHelperTv: null,
  normal: null,
  arrowHelperNormal: null,
  origin: null,
  stepForMovements: 0.05,
  point: null,
  vectorLength: 1,
  resetTangentsAndNormal: function () {
    this.vectorTu = null;
    this.arrowHelperTu = null;
    this.vectorTv = null;
    this.arrowHelperTv = null;
    this.normal = null;
    this.arrowHelperNormal = null;
    this.origin = null;
    this.point = null;
  },
};

const keyMoveOperationsMap = {
  ["KeyA"]: () =>
    (tangentsAndNormalParameters.setU =
      tangentsAndNormalParameters.u -
      tangentsAndNormalParameters.stepForMovements),
  ["KeyD"]: () =>
    (tangentsAndNormalParameters.setU =
      tangentsAndNormalParameters.u +
      tangentsAndNormalParameters.stepForMovements),
  ["KeyW"]: () =>
    (tangentsAndNormalParameters.setV =
      tangentsAndNormalParameters.v +
      tangentsAndNormalParameters.stepForMovements),
  ["KeyS"]: () =>
    (tangentsAndNormalParameters.setV =
      tangentsAndNormalParameters.v -
      tangentsAndNormalParameters.stepForMovements),
};

const removeTangentsAndNormalFromScene = () => {
  scene.remove(tangentsAndNormalParameters.vectorTu);
  scene.remove(tangentsAndNormalParameters.arrowHelperTu);
  scene.remove(tangentsAndNormalParameters.vectorTv);
  scene.remove(tangentsAndNormalParameters.arrowHelperTv);
  scene.remove(tangentsAndNormalParameters.normal);
  scene.remove(tangentsAndNormalParameters.arrowHelperNormal);
  scene.remove(tangentsAndNormalParameters.origin);
  scene.remove(tangentsAndNormalParameters.point);
};

const onChangeFigureParameters = () => {
  figureHeight = +form.elements.height.value;
  figureP = +form.elements.p.value;
  useWireframe = !!form.elements.useWireframe.checked;
  initializeApp();
  animate();
};

const getWebGLRenderer = (canvasElement) => {
  return new THREE.WebGLRenderer({
    canvas: canvasElement,
  });
};

const initializeCamera = () => {
  perspectiveCamera = new THREE.PerspectiveCamera(
    CONFIG.fov,
    CONFIG.aspect,
    CONFIG.near,
    CONFIG.far
  );

  perspectiveCamera.position.z = cameraPositionZ;
};

const initializeOrbitControls = () => {
  orbitControls = new THREE.OrbitControls(
    perspectiveCamera,
    webGLRenderer.domElement
  );
  orbitControls.update();
};

const initializeScene = () => {
  scene = new THREE.Scene();
  scene.background = new THREE.Color(CONFIG.colors.background);
};

const initializeMaterial = () => {
  meshBasicMaterial = new THREE.MeshBasicMaterial({
    color: CONFIG.colors.figure,
    wireframe: useWireframe,
  });
};

const getX = (z, h, p, beta) => {
  return ((Math.abs(z) - h) ** 2 / (2 * p)) * Math.cos(beta);
};

const getY = (z, h, p, beta) => {
  return ((Math.abs(z) - h) ** 2 / (2 * p)) * Math.sin(beta);
};

const getZ = (z) => {
  return z;
};

const convertUVIntoFunctionParameters = (u, v) => {
  const z = v * 2 * figureHeight - figureHeight;
  const beta = u * Math.PI * 2;

  return { z, beta };
};

const parabolicHummingTop = (u, v, target) => {
  const { z, beta } = convertUVIntoFunctionParameters(u, v);

  const calculatedX = getX(z, figureHeight, figureP, beta);
  const calculatedY = getY(z, figureHeight, figureP, beta);
  const calculatedZ = getZ(z);

  target.set(calculatedX, calculatedY, calculatedZ);
};

const setupMesh = () => {
  mesh = new THREE.Mesh(parametricGeometry, meshBasicMaterial);
};

const initializeGeometry = () => {
  parametricGeometry = new THREE.ParametricGeometry(
    parabolicHummingTop,
    slices,
    stacks
  );
};

const addMeshToScene = () => {
  scene.add(mesh);
};

const checkSize = () => {
  const { clientWidth, clientHeight, width, height } = webGLRenderer.domElement;
  const shouldSetSize = height !== clientHeight || width !== clientWidth;

  if (shouldSetSize) {
    webGLRenderer.setSize(clientWidth, clientHeight, false);
    perspectiveCamera.aspect = clientWidth / clientHeight;
    const guiScalar = perspectiveCamera.aspect / 3;
    tangentsAndNormalParameters.vectorLength = guiScalar;
    perspectiveCamera.updateProjectionMatrix();
  }
};

const getTuDerivatives = (z, h, p, beta) => {
  const DxDu = ((Math.abs(z) - h) ** 2 / (2 * p)) * -Math.sin(beta);
  const DyDu = ((Math.abs(z) - h) ** 2 / (2 * p)) * Math.cos(beta);
  const DzDu = 0;

  return { DxDu, DyDu, DzDu };
};

const getTvDerivatives = (z, h, p, beta) => {
  const DxDv = (z * Math.cos(beta) * (Math.abs(z) - h)) / (p * Math.abs(z));
  const DyDv = (z * Math.sin(beta) * (Math.abs(z) - h)) / (p * Math.abs(z));
  const DzDv = 1;

  return { DxDv, DyDv, DzDv };
};

const setupTangentTu = ({ z, beta, calculatedX, calculatedY, calculatedZ }) => {
  const { DxDu, DyDu, DzDu } = getTuDerivatives(z, figureHeight, figureP, beta);

  const tu = new THREE.Vector3(DxDu, DyDu, DzDu);
  tu.normalize();
  tangentsAndNormalParameters.vectorTu = tu;
  tangentsAndNormalParameters.origin = new THREE.Vector3(
    calculatedX,
    calculatedY,
    calculatedZ
  );
  const color = 0x0000ff;
  tangentsAndNormalParameters.arrowHelperTu = new THREE.ArrowHelper(
    tangentsAndNormalParameters.vectorTu,
    tangentsAndNormalParameters.origin,
    tangentsAndNormalParameters.vectorLength,
    color
  );
  scene.add(tangentsAndNormalParameters.arrowHelperTu);
};

const setupTangentTv = ({ z, beta, calculatedX, calculatedY, calculatedZ }) => {
  const { DxDv, DyDv, DzDv } = getTvDerivatives(z, figureHeight, figureP, beta);

  const tv = new THREE.Vector3(DxDv, DyDv, DzDv);
  tv.normalize();
  tangentsAndNormalParameters.vectorTv = tv;
  tangentsAndNormalParameters.origin = new THREE.Vector3(
    calculatedX,
    calculatedY,
    calculatedZ
  );
  const color = 0xff0000;
  tangentsAndNormalParameters.arrowHelperTv = new THREE.ArrowHelper(
    tangentsAndNormalParameters.vectorTv,
    tangentsAndNormalParameters.origin,
    tangentsAndNormalParameters.vectorLength,
    color
  );
  scene.add(tangentsAndNormalParameters.arrowHelperTv);
};

const setupNormal = ({ calculatedX, calculatedY, calculatedZ }) => {
  tangentsAndNormalParameters.normal = new THREE.Vector3();
  tangentsAndNormalParameters.normal.crossVectors(
    tangentsAndNormalParameters.vectorTu,
    tangentsAndNormalParameters.vectorTv
  );
  tangentsAndNormalParameters.normal.normalize();
  tangentsAndNormalParameters.origin = new THREE.Vector3(
    calculatedX,
    calculatedY,
    calculatedZ
  );

  const color = 0xffffff;
  tangentsAndNormalParameters.arrowHelperNormal = new THREE.ArrowHelper(
    tangentsAndNormalParameters.normal,
    tangentsAndNormalParameters.origin,
    tangentsAndNormalParameters.vectorLength,
    color
  );

  scene.add(tangentsAndNormalParameters.arrowHelperNormal);
};

const drawPointOnSurface = ({ calculatedX, calculatedY, calculatedZ }) => {
  const geometry = new THREE.SphereGeometry(0.01, 32, 32);
  const material = new THREE.MeshBasicMaterial({ color: 0xffff00 });
  tangentsAndNormalParameters.point = new THREE.Mesh(geometry, material);
  tangentsAndNormalParameters.point.position.set(
    calculatedX,
    calculatedY,
    calculatedZ
  );
  scene.add(tangentsAndNormalParameters.point);
};

const setupTangentsAndNormal = ({
  z,
  beta,
  calculatedX,
  calculatedY,
  calculatedZ,
}) => {
  setupTangentTu({ z, beta, calculatedX, calculatedY, calculatedZ });

  setupTangentTv({
    z,
    beta,
    calculatedX,
    calculatedY,
    calculatedZ,
  });

  setupNormal({ calculatedX, calculatedY, calculatedZ });
};

const startNormalProcess = () => {
  const [u, v] = [tangentsAndNormalParameters.u, tangentsAndNormalParameters.v];
  const { z, beta } = convertUVIntoFunctionParameters(u, v);

  const calculatedX = getX(z, figureHeight, figureP, beta);
  const calculatedY = getY(z, figureHeight, figureP, beta);
  const calculatedZ = getZ(z);

  drawPointOnSurface({ calculatedX, calculatedY, calculatedZ });

  setupTangentsAndNormal({ z, beta, calculatedX, calculatedY, calculatedZ });
};

const initializeApp = () => {
  const canvas = document.getElementById("canvas");
  webGLRenderer = getWebGLRenderer(canvas);

  initializeCamera();
  initializeOrbitControls();
  initializeScene();
  initializeMaterial();
  initializeGeometry();

  setupMesh();
  addMeshToScene();

  startNormalProcess();
};

const animate = () => {
  checkSize();

  webGLRenderer.render(scene, perspectiveCamera);

  requestAnimationFrame(animate);
};

document.addEventListener("DOMContentLoaded", () => {
  const handler = (event) => {
    keyMoveOperationsMap[event.code]();
    removeTangentsAndNormalFromScene();
    tangentsAndNormalParameters.resetTangentsAndNormal();
    startNormalProcess();
  };

  document.addEventListener("keypress", handler);

  onChangeFigureParameters();
  initializeApp();
  animate();
});
