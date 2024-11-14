import React from 'react';
import axios from 'axios';

interface ImageUploaderProps {
    onUpload: (url: string) => void;
}

const ImageUploader: React.FC<ImageUploaderProps> = ({ onUpload }) => {

    const handleFileChange = async (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files[0]) {
            const selectedFile = event.target.files[0];
            const formData = new FormData();
            formData.append('file', selectedFile);

            try {
                const response = await axios.post('http://localhost:8080/api/images/upload', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });
                onUpload(response.data); // Pass the URL of the uploaded file back to the parent component
            } catch (error) {
                console.error('Error uploading file:', error);
            }
        }
    };

    return (
        <div className="mt-4">
            <input
                accept="image/*"
                style={{ display: 'none' }}
                id="upload-button"
                type="file"
                onChange={handleFileChange}
            />
            <label htmlFor="upload-button" className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500 cursor-pointer">
                Upload
            </label>
        </div>
    );
};

export default ImageUploader;
