
<!-- Left side column. contains the logo and sidebar -->
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel -->
		<div class="user-panel">
			<div class="pull-left image">
				<img src="dist/img/avatar5.png" class="img-circle"
					alt="User Image">
			</div>
			<div class="pull-left info">
				<p>Carlos López</p>
				<a href="#"><i class="fa fa-circle text-success"></i> En Linea</a>
			</div>
		</div>
		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<li class="header">MENU PRINCIPAL</li>
			<li class="<?php if($vista->page == "tc"){ echo " active "; } ?>"><a
				href="index.php"> <i class="fa fa-dashboard"></i> <span>Tablero de
						Control</span>
			</a></li>
			<li
				class="treeview <?php if($vista->page == "cc" || $vista->page == "ec" || $vista->page == "ii" || $vista->page == "eca" ){ echo " active "; } ?>"><a
				href="#"> <i class="fa  fa-money"></i> <span>Administración de
						Cartera</span> <span class="pull-right-container"> <span
						class="label label-primary pull-right">4</span>
				</span>
			</a>
				<ul class="treeview-menu">
					<!--   pages/layout/top-nav.html-->
					<li class="<?php if($vista->page == "cc"){ echo " active "; } ?>"><a
						href="index.php?page=cc"><i class="fa fa-circle-o"></i> Cargar
							Cartera</a></li>
					<li class="<?php if($vista->page == "ec"){ echo " active "; } ?>"><a
						href="#"><i class="fa fa-circle-o"></i> Erores de carga</a></li>
					<li class="<?php if($vista->page == "ii"){ echo " active "; } ?>"><a
						href="#"><i class="fa fa-circle-o"></i> Ingreso Individual</a></li>
					<li class="<?php if($vista->page == "eca"){ echo " active "; } ?>"><a
						href="#"><i class="fa fa-circle-o"></i> Editar Cartera</a></li>
				</ul></li>
		</ul>

	</section>
	<!-- /.sidebar -->

</aside>