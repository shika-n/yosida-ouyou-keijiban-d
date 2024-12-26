PGDMP                 
        |            keijiban    12.18    12.18                 0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            !           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            "           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            #           1262    16413    keijiban    DATABASE     �   CREATE DATABASE keijiban WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Japanese_Japan.932' LC_CTYPE = 'Japanese_Japan.932';
    DROP DATABASE keijiban;
                postgres    false            �            1259    16454    likes    TABLE     Z   CREATE TABLE public.likes (
    post_id integer NOT NULL,
    user_id integer NOT NULL
);
    DROP TABLE public.likes;
       public         heap    postgres    false            �            1259    16419    posts    TABLE     �   CREATE TABLE public.posts (
    id integer NOT NULL,
    user_id integer NOT NULL,
    content character varying(256) NOT NULL,
    parent_post_id integer,
    "timestamp" timestamp with time zone NOT NULL
);
    DROP TABLE public.posts;
       public         heap    postgres    false            �            1259    16446    posts_id_seq    SEQUENCE     �   ALTER TABLE public.posts ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.posts_id_seq
    START WITH 11
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    203            �            1259    16414    users    TABLE       CREATE TABLE public.users (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    usertag character varying(20) NOT NULL,
    email character varying(50) NOT NULL,
    password character varying(256) NOT NULL,
    description character varying(256)
);
    DROP TABLE public.users;
       public         heap    postgres    false            �            1259    16448    users_id_seq    SEQUENCE     �   ALTER TABLE public.users ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 3
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    202                      0    16454    likes 
   TABLE DATA           1   COPY public.likes (post_id, user_id) FROM stdin;
    public          postgres    false    206   �                 0    16419    posts 
   TABLE DATA           R   COPY public.posts (id, user_id, content, parent_post_id, "timestamp") FROM stdin;
    public          postgres    false    203                    0    16414    users 
   TABLE DATA           T   COPY public.users (id, username, usertag, email, password, description) FROM stdin;
    public          postgres    false    202   N#       $           0    0    posts_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.posts_id_seq', 76, true);
          public          postgres    false    204            %           0    0    users_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.users_id_seq', 5, true);
          public          postgres    false    205            �
           2606    16445    users email_unique 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT email_unique UNIQUE (email);
 <   ALTER TABLE ONLY public.users DROP CONSTRAINT email_unique;
       public            postgres    false    202            �
           2606    16458    likes likes_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.likes
    ADD CONSTRAINT likes_pkey PRIMARY KEY (post_id, user_id);
 :   ALTER TABLE ONLY public.likes DROP CONSTRAINT likes_pkey;
       public            postgres    false    206    206            �
           2606    16423    posts posts_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.posts
    ADD CONSTRAINT posts_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.posts DROP CONSTRAINT posts_pkey;
       public            postgres    false    203            �
           2606    16418    users user_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);
 9   ALTER TABLE ONLY public.users DROP CONSTRAINT user_pkey;
       public            postgres    false    202            �
           2606    16453    users usertag_unique 
   CONSTRAINT     R   ALTER TABLE ONLY public.users
    ADD CONSTRAINT usertag_unique UNIQUE (usertag);
 >   ALTER TABLE ONLY public.users DROP CONSTRAINT usertag_unique;
       public            postgres    false    202            �
           1259    16471    email_index    INDEX     >   CREATE INDEX email_index ON public.users USING btree (email);
    DROP INDEX public.email_index;
       public            postgres    false    202            �
           1259    16469    parent_post_id_index    INDEX     P   CREATE INDEX parent_post_id_index ON public.posts USING btree (parent_post_id);
 (   DROP INDEX public.parent_post_id_index;
       public            postgres    false    203            �
           1259    16470    user_id_index    INDEX     B   CREATE INDEX user_id_index ON public.posts USING btree (user_id);
 !   DROP INDEX public.user_id_index;
       public            postgres    false    203            �
           2606    16459    likes post_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.likes
    ADD CONSTRAINT post_id_fk FOREIGN KEY (post_id) REFERENCES public.posts(id) ON UPDATE CASCADE ON DELETE CASCADE;
 :   ALTER TABLE ONLY public.likes DROP CONSTRAINT post_id_fk;
       public          postgres    false    203    206    2707            �
           2606    16439    posts posts_fk_posts    FK CONSTRAINT     �   ALTER TABLE ONLY public.posts
    ADD CONSTRAINT posts_fk_posts FOREIGN KEY (parent_post_id) REFERENCES public.posts(id) ON UPDATE CASCADE ON DELETE CASCADE NOT VALID;
 >   ALTER TABLE ONLY public.posts DROP CONSTRAINT posts_fk_posts;
       public          postgres    false    203    203    2707            �
           2606    16424    posts posts_fk_users    FK CONSTRAINT     �   ALTER TABLE ONLY public.posts
    ADD CONSTRAINT posts_fk_users FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE CASCADE ON DELETE CASCADE;
 >   ALTER TABLE ONLY public.posts DROP CONSTRAINT posts_fk_users;
       public          postgres    false    2702    202    203            �
           2606    16464    likes user_id_fk    FK CONSTRAINT     �   ALTER TABLE ONLY public.likes
    ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES public.users(id) ON UPDATE CASCADE ON DELETE CASCADE;
 :   ALTER TABLE ONLY public.likes DROP CONSTRAINT user_id_fk;
       public          postgres    false    206    2702    202               0   x�34�4�22Ɔ � �2�2bc3Na�eh�r���b���� ���         8  x��WK�U^�����1�ܩ��G�|��1��(�I����T?����&�h0"�D�]���Sd�+��߹�z��ʵ+�Jw�w^�y����l�ND9���㹷sړ�ԛ~�)%�������kȷF�t�N�����:TwDTA�'�7�<���I�o�ʥV����r�q9�Y��r�a�xP.������������w�����?"U]��t�-�@�tvH ���`p�
YԀzF�W��������/��;��45A��QP��V���/V�^�����GWzb�OO��8Y�HS!��������	(6��8�3�`��
���JZ"&LǄ���Ql$���+��+�dx�Jr�E$�h�}?�"���[-S����r��rq�^X:>�6<���p|��cG\ě�+?��澱 �+��J��G�h¦Y��AZl��h8M{EZ�&,�g�l�ˆX�gg�}���l�rұ��I6-��g)�<�g�8)� �  1��I��4�M�}p�c�t���Y�'6���I�|��p�?�X�c�,�F}�{l7�ey6ͦ,��V���<�zY1�gC��F���"����]h�Q���#V"ʓ��@˓T�(��;�ߺ���Kʁm����55h�j���e:���C;d�g9�\�H��;&��H�Q�{Ց
%��%���G$��u�5��;P}��n�y�䇧Ξ8��*э��¡�B
I����.�4��l4��G��!�e��s��EB��{u�j�4z���&�8]�v'�+�?LW���q�C�V)&[�d��f�A��j�3��VV.\�OF�Y��$�9n*c�Rh�����2J����ִ���P����(��5b��ʘg^ᐪ|M+`M�b�b�%*�jgX�t��(��+A%+��Eg�Q���ө*�i��|e��LZ��F�����4���$MV��������=*�h�T�,E.�*���Sad�����=*S@���.
�X�	D��R@���4U 	BM��ѳ���۷]��#�Z@(nw�i��!B��z��OD=6mZP`\�3Z[�c�rc��"ʱ��#�ʡ�t���:Pt.ǆX�	��,o�
3D;�x��(���k�w�x�A���2�e��8�L�yt��J8��<�iPTȕ+hC�c����>��/؅�3�����V��Nq��[D��� ڴ���� �D��vƊ훀h?ъ1(��F믷��3g�HO<�R1���F��������7�-��﵂�౴,vδ�P-#��vP�Rw߻� ԝt]��A��5���/���.�������rq�\�R�����V��Z�QL��
�a�3�N��kſ�ak�6���m��brN�M�M�ʹ��j'տ
��.�N�ؖDH��U�n�¦
��ߙ؞�!F�������m�cZ|j��!���2��d�����nl~�en1��=llnrz�����y϶������Lwk�w��z%���q��g{G��"{KY|6�R�=e{{lo�w�A���zw��z�֣��
�l��𵵵 d�         �  x��Q[k�`�NE��&=^֬]Os=�� �4M�,k��͚4cK�c*8d��b(*��P��~��6w��`�v�J_�<���I���0�T�>
l\c�I3ϋ����L��%Th�j��Ř?�E�����p�t#�5WfPh��e%��8�7�]m-�J%jx�'w�R���G�J��$�����t� ��@$�,��K�@��@u?�Áh�i'�Ú.5$Q��Q\�F�|n=�����@�j�]Qs>1���b��b�Uj�<���|�H�ɉ	`<�` s���i�7�<�G`����C��S��g����F߁�mMQ�|���vJ�ufE�g��|��/�B���-�p��!-�d3��D�n� X�h��b��Y�=��_&Q���3�o���g�ֱ�`Ӱ���ɓ/�o���������q�wv��W/":�����6N����q� E�A���$Nk�~�%���,�e+�|���h$�����"��tr�$S����lq����K��/Ĩ��ƧF�`���x
�Gg�/��`LN# ��F&0F$�3��#��n�)d��ܒp_�]��F�#wYf��¡��U��A�W���*�.w�V&��J)�cy��@"'k�i{G�f����r~���ΛE;7(���HQu��KQ7e�v{=s�M�b�U��Ew�DۀX�{�a��_oߦ���B{(��iOݖWy��{�5��6�;(����]}��     