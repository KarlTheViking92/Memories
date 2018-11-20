<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-default navbar-inverse" role="navigation">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">MEMORY
				GAME</a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a
					href="${pageContext.request.contextPath}/">Home</a></li>
				<c:if test="${username != null}">
					<li><a href="${pageContext.request.contextPath}/matchHistory">Match
							history</a></li>
				</c:if>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">More<span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="${pageContext.request.contextPath}//joinLobby">Join
								a lobby</a></li>
						<li><a href="#">Create a lobby</a></li>
						<li><a href="#">Something else here</a></li>
						<li class="divider"></li>
						<li><a href="#">Match history</a></li>
						<li class="divider"></li>
						<li><a href="${pageContext.request.contextPath}/rules">Rules</a></li>
					</ul></li>
			</ul>
			<form class="navbar-form navbar-left" role="search">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search">
				</div>
				<button type="submit" class="btn btn-default">Submit</button>
			</form>
			<%-- <c:if test="${username == null}">
		<a href="${pageContext.request.contextPath}/login">Login</a>
	</c:if>
	<c:if test="${username != null}">
Hello ${username} | <a
			href="${pageContext.request.contextPath}/logout">Logout</a>
	</c:if> --%>
			<c:if test="${username == null}">
				<%-- <a href="${pageContext.request.contextPath}/login">Login</a> --%>
				<ul class="nav navbar-nav navbar-right">
					<li><p class="navbar-text">Already have an account?</p></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><b>Login</b> <span class="caret"></span></a>
						<ul id="login-dp" class="dropdown-menu">
							<li>
								<div class="row" id="login-form">
									<div class="col-md-12">
										<h4>Login</h4>
										<div class="social-buttons"></div>
										<form class="form" role="form" action="login"
											accept-charset="UTF-8" id="login-nav">
											<div class="form-group">
												<label class="sr-only" for="exampleInputEmail2">Username</label>
												<input type="text" class="form-control"
													id="exampleInputEmail2" placeholder="Username"
													name="username" required>
											</div>
											<div class="form-group">
												<label class="sr-only" for="exampleInputPassword2">Password</label>
												<input type="text" class="form-control"
													id="exampleInputPassword2" placeholder="Password"
													name="password" required>
												<div class="help-block text-right">
													<a href="">Forget the password ?</a>
												</div>
											</div>
											<div class="form-group">
												<button type="submit" class="btn btn-primary btn-block">Sign
													in</button>
											</div>
											<div class="checkbox">
												<label> <input type="checkbox"> keep me
													logged-in
												</label>
											</div>
											<c:if test="${not empty errorLogin}">
												<div class="alert alert-danger alert-dismissible"
													role="alert">
													<button type="button" class="close" data-dismiss="alert"
														aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
													<strong>${errorLogin}</strong>
												</div>
											</c:if>
										</form>
									</div>
									<div class="bottom text-center">
										New here ? <a href="#" id="register-form-link"><b>Join
												Us</b></a>
									</div>
								</div>
								<div class="row" id="register-form" style="display: none;">
									<div class="col-md-12">
										<h4>Register</h4>
										<div class="social-buttons"></div>
										<form class="form" role="form" action="registration"
											accept-charset="UTF-8" id="login-nav">
											<div class="form-group">
												<label class="sr-only" for="exampleInputEmail2">Email
													address</label> <input type="text" class="form-control"
													id="exampleInputEmail2" placeholder="Email address"
													name="username" required>
											</div>
											<div class="form-group">
												<label class="sr-only" for="exampleInputPassword2">Password</label>
												<input type="password" class="form-control"
													id="exampleInputPassword2" placeholder="Password"
													name="password" required>
											</div>
											<div class="form-group">
												<button type="submit" class="btn btn-primary btn-block">Sign
													up</button>
											</div>
											<div class="checkbox">
												<label> <input type="checkbox"> keep me
													logged-in
												</label>
											</div>
											<c:if test="${not empty errorRegistration}">
												<div class="alert alert-danger alert-dismissible"
													role="alert">
													<button type="button" class="close" data-dismiss="alert"
														aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
													<strong>${errorRegistration}</strong>
												</div>
											</c:if>
										</form>
									</div>
									<div class="bottom text-center">
										Already a member ? <a href="#" id="login-form-link"><b>Go
												and log in</b></a>
									</div>
								</div>
							</li>
						</ul></li>
				</ul>
			</c:if>
			<c:if test="${username != null}">
				<ul class="nav navbar-nav navbar-right">
					<li><p class="navbar-text">
							<font color="white">Welcome ${username}</font>
						</p></li>
					<li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
				</ul>
			</c:if>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>