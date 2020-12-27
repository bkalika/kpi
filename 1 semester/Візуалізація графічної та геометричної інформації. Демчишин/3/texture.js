 var LODs=[
        "https://i.postimg.cc/PNtbm4Ss/0.png",
        "https://i.postimg.cc/zVvnQfmx/1.png",
        "https://i.postimg.cc/SJPWbpkk/2.png",
        "https://i.postimg.cc/Yjb68WP4/3.png"];

function mipmap( size, url ) {
            var imageCanvas = document.createElement( "canvas" );
            var context = imageCanvas.getContext( "2d" );
            var img = new Image();
            img.crossOrigin = "";
            imageCanvas.width = imageCanvas.height = size;
            img.src=url;
            img.onload = function(){  
                context.fillStyle = context.createPattern(img, "repeat");
                context.fillRect( 0,0,size,size);
            }
            context.fillRect( 0,0,size,size );
            
            return imageCanvas;
        }

        var texture = new THREE.TextureLoader().load(LODs[0]);
            texture.mipmaps[0] = mipmap(8, LODs[0]);
            texture.mipmaps[1] = mipmap(4, LODs[1]);
            texture.mipmaps[2] = mipmap(2, LODs[2]);
            texture.mipmaps[3] = mipmap(1, LODs[3]);
          
        console.log(texture.mipmaps);
        texture.wrapS =THREE.RepeatWrapping;
        texture.wrapT = THREE.RepeatWrapping;
        texture.minFilter = THREE.LinearMipMapNearestFilter;
        
        texture.needsUpdate = true;
        texture.repeat.set( 100,100 );
        var material = new THREE.MeshBasicMaterial({ map: texture });

        var geometry = new THREE.ParametricGeometry(snailFunc, 250,250);
        var SnailFigure = new THREE.Mesh(geometry, material);
        scene.add(SnailFigure);