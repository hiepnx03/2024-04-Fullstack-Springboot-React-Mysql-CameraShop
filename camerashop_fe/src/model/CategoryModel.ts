class CategoryModel {
    idCategory: number;          // ID của danh mục
    categoryName?: string;       // Tên danh mục
    idProducts?: number[];


    constructor(idCategory: number, categoryName: string, idProducts: number[]) {
        this.idCategory = idCategory;
        this.categoryName = categoryName;
        this.idProducts = idProducts;
    }
}

export default CategoryModel;
