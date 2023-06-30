<?php

namespace App\Controller;

use App\Entity\User1;

use App\Form\UserType;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

class RegistrationController extends AbstractController
{

    private $passwordEncoder;



    public function __construct(UserPasswordEncoderInterface $passwordEncoder)

    {

        $this->passwordEncoder = $passwordEncoder;
    }



    /**

     * @Route("/registration@1725", name="registration")

     */

    public function index(Request $request)

    {

        $user = new User1();



        $form = $this->createForm(UserType::class, $user);



        $form->handleRequest($request);



        if ($form->isSubmitted() && $form->isValid()) {

            // Encode the new users password

            $user->setPassword($this->passwordEncoder->encodePassword($user, $user->getPassword()));



            // Set their role

            $user->setRoles(['ROLE_USER']);



            // Save

            $em = $this->getDoctrine()->getManager();

            $em->persist($user);

            $em->flush();



            return $this->redirectToRoute('app_login');
        }



        return $this->render('registration/index.html.twig', [

            'form' => $form->createView(),

        ]);
    }
}
