class CategoryModel {
    idCategory: number;
    categoryName?: string;

    constructor(idCategory: number, categoryName: string) {
        this.idCategory = idCategory;
        this.categoryName = categoryName;
    }
}

export default CategoryModel;