class Category {
    id: number;
    name: string; // Category name must be between 2 and 100 characters (validation handled in the UI)
    description?: string; // Optional field
    image?: string; // Optional field
    active: boolean;
    deleted: boolean;
    editable: boolean;
    visible: boolean;
    slug?: string; // Optional field
    status?: number; // Optional field
    products?: number[];

    constructor(id: number, name: string, description: string, image: string, active: boolean, deleted: boolean, editable: boolean, visible: boolean, slug: string, status: number, products: number[]) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.active = active;
        this.deleted = deleted;
        this.editable = editable;
        this.visible = visible;
        this.slug = slug;
        this.status = status;
        this.products = products;
    }
}

export default Category;
