import React from "react";
import { Link, useLocation } from "react-router-dom";

function Footer() {
    return (
        // <!-- Footer -->
        <footer className='bg-primary text-center text-white'>
            {/* <!-- Grid container --> */}
            <div className='container p-4'>
                {/* <!-- Section: Social media --> */}
                <section className='mb-4'>
                    {/* <!-- Facebook --> */}
                    <a
                        className='btn btn-outline-light btn-floating m-1'
                        href='#!'
                        role='button'
                    >
                        <i className='fab fa-facebook-f'></i>
                    </a>

                    {/* <!-- Twitter --> */}
                    <a
                        className='btn btn-outline-light btn-floating m-1'
                        href='#!'
                        role='button'
                    >
                        <i className='fab fa-twitter'></i>
                    </a>

                    {/* <!-- Google --> */}
                    <a
                        className='btn btn-outline-light btn-floating m-1'
                        href='#!'
                        role='button'
                    >
                        <i className='fab fa-google'></i>
                    </a>

                    {/* <!-- Instagram --> */}
                    <a
                        className='btn btn-outline-light btn-floating m-1'
                        href='#!'
                        role='button'
                    >
                        <i className='fab fa-instagram'></i>
                    </a>

                    {/* <!-- Linkedin --> */}
                    <a
                        className='btn btn-outline-light btn-floating m-1'
                        href='#!'
                        role='button'
                    >
                        <i className='fab fa-linkedin-in'></i>
                    </a>

                    {/* <!-- Github --> */}
                    <a
                        className='btn btn-outline-light btn-floating m-1'
                        href='#!'
                        role='button'
                    >
                        <i className='fab fa-github'></i>
                    </a>
                </section>
                {/* <!-- Section: Social media --> */}

                {/* <!-- Section: Form --> */}
                <section className=''>
                    <form action=''>
                        {/* <!--Grid row--> */}
                        <div className='row d-flex justify-content-center'>
                            <div className='col-auto'>
                                <p className='pt-2'>
                                    <strong>Đăng ký nhận bản tin</strong>
                                </p>
                            </div>

                            <div className='col-md-5 col-12'>
                                {/* <!-- Email input --> */}
                                <div className=' form-white mb-4'>
                                    <input
                                        type='email'
                                        id='form5Example21'
                                        className='form-control'
                                        placeholder='Nhập Email'
                                    />
                                </div>
                            </div>

                            <div className='col-auto'>
                                {/* <!-- Submit button --> */}
                                <button
                                    type='button'
                                    className='btn btn-outline-light mb-4'
                                >
                                    Đăng ký
                                </button>
                            </div>
                        </div>
                        {/* <!--Grid row--> */}
                    </form>
                </section>
                {/* <!-- Section: Form --> */}

                {/* <!-- Section: Links --> */}
                <section className=''>
                    {/* <!--Grid row--> */}
                    <div className='row'>
                        <div className='col-lg-6 col-md-12'>
                            <div className='row'>
                                <div className='col-lg-4 col-md-12 mb-4'>
                                    <h5 className='text-uppercase'>DỊCH VỤ</h5>

                                    <ul className='list-unstyled mb-0'>
                                        <li>
                                            <a href='#!' className='text-white'>
                                                Điều khoản sử dụng
                                            </a>
                                        </li>
                                        <li>
                                            <a href='#!' className='text-white'>
                                                Chính sách bảo mật thông tin cá nhân
                                            </a>
                                        </li>
                                        <li>
                                            <a href='#!' className='text-white'>
                                                Chính sách bảo mật thanh toán
                                            </a>
                                        </li>
                                        <li>
                                            <a href='#!' className='text-white'>
                                                Hệ thống trung tâm - nhà sách
                                            </a>
                                        </li>
                                    </ul>
                                </div>

                                <div className='col-lg-4 col-md-12 mb-4'>
                                    <h5 className='text-uppercase'>HỖ TRỢ</h5>

                                    <ul className='list-unstyled mb-0'>
                                        <li>
                                            <a href='#!' className='text-white'>
                                                Chính sách đổi - trả - hoàn tiền
                                            </a>
                                        </li>
                                        <li>
                                            <a href='#!' className='text-white'>
                                                Chính sách bảo hành - bồi hoàn
                                            </a>
                                        </li>
                                        <li>
                                            <a href='#!' className='text-white'>
                                                Chính sách vận chuyển
                                            </a>
                                        </li>
                                        <li>
                                            <a href='#!' className='text-white'>
                                                Chính sách khách sỉ
                                            </a>
                                        </li>
                                    </ul>
                                </div>

                                <div className='col-lg-4 col-md-12 mb-4'>
                                    <h5 className='text-uppercase'>TÀI KHOẢN CỦA TÔI</h5>

                                    <ul className='list-unstyled mb-0'>
                                        <li>
                                            <Link to={"/login"} className='text-white'>
                                                Đăng nhập/Tạo mới tài khoản
                                            </Link>
                                        </li>
                                        <li>
                                            <a href='#!' className='text-white'>
                                                Thay đổi địa chỉ khách hàng
                                            </a>
                                        </li>
                                        <li>
                                            <a href='#!' className='text-white'>
                                                Chi tiết tài khoản
                                            </a>
                                        </li>
                                        <li>
                                            <a href='#!' className='text-white'>
                                                Lịch sử mua hàng
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div className='col-lg-6 col-md-12'>
                            <iframe
                                title="map"
                                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3724.5103318147258!2d105.79967277425571!3d21.012256888347174!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135ab5f7e8677a7%3A0xe8e81820d9c9ed9b!2sUniversity%20of%20Labour%20%26%20Social%20Affairs%20(ULSA)!5e0!3m2!1sen!2s!4v1713301536276!5m2!1sen!2s"
                                width="500"
                                height="200"
                                style={{ border: 0 }}
                                loading='lazy'
                                referrerPolicy='no-referrer-when-downgrade'
                            ></iframe>
                        </div>
                    </div>
                    {/* <!--Grid row--> */}
                </section>
                {/* <!-- Section: Links --> */}
            </div>
            {/* <!-- Grid container --> */}

            {/* <!-- Copyright --> */}
            <div
                className='text-center p-3'
                style={{ backgroundColor: "rgba(0, 0, 0, 0.2)" }}
            >
                © 2024 Copyright
                <span className='text-white text-decoration-underline'>
					{" "}
				</span>
            </div>
            {/* <!-- Copyright --> */}
        </footer>
    );
}

export default Footer;
