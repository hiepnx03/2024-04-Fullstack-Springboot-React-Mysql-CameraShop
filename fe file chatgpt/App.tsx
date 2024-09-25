import React from 'react';
import { BrowserRouter as Router, Route, Switch, Link } from 'react-router-dom';
import ProductList from "./components/ProductList";
import CategoryList from "./components/CategoryList";
import ImageList from "./components/ImageList";

const App: React.FC = () => {
  return (
      <Router>
        <div>
          <nav>
            <ul>
              <li><Link to="/products">Products</Link></li>
              <li><Link to="/categories">Categories</Link></li>
              <li><Link to="/images">Images</Link></li>
            </ul>
          </nav>
          <Switch>
            <Route path="/products" component={ProductList} />
            <Route path="/categories" component={CategoryList} />
            <Route path="/images" component={ImageList} />
          </Switch>
        </div>
      </Router>
  );
};

export default App;
