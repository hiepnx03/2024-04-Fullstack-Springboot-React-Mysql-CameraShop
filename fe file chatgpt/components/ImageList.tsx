import React, { useEffect, useState } from 'react';
import { ImageDTO } from '../types';
import { getImages } from '../services/ImageService';

const ImageList: React.FC = () => {
    const [images, setImages] = useState<ImageDTO[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            const result = await getImages();
            setImages(result);
        };

        fetchData();
    }, []);

    return (
        <div>
            <h1>Images</h1>
            <ul>
                {images.map((image) => (
                    <li key={image.idImage}>
                        <img src={image.urlImage} alt={image.nameImage} width="100" />
                        {image.nameImage}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ImageList;
