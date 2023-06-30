<?php

namespace App\Controller;

use App\Entity\User1;
use App\Form\User1Type;
use App\Repository\User1Repository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

#[Route('/user1')]
class User1Controller extends AbstractController
{
    private $passwordEncoder;
    public function __construct(UserPasswordEncoderInterface $passwordEncoder)

    {

        $this->passwordEncoder = $passwordEncoder;
    }
    #[Route('/', name: 'app_user1_index', methods: ['GET'])]
    public function index(User1Repository $user1Repository): Response
    {
        return $this->render('user1/index.html.twig', [
            'user1s' => $user1Repository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_user1_new', methods: ['GET', 'POST'])]
    public function new(Request $request, User1Repository $user1Repository): Response
    {
        $user1 = new User1();
        $form = $this->createForm(User1Type::class, $user1);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $user1->setPassword($this->passwordEncoder->encodePassword($user1, $user1->getPassword()));
            $user1Repository->add($user1);
            return $this->redirectToRoute('app_user1_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('user1/new.html.twig', [
            'user1' => $user1,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_user1_show', methods: ['GET'])]
    public function show(User1 $user1): Response
    {
        return $this->render('user1/show.html.twig', [
            'user1' => $user1,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_user1_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, User1 $user1, User1Repository $user1Repository): Response
    {
        $form = $this->createForm(User1Type::class, $user1);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $user1->setPassword($this->passwordEncoder->encodePassword($user1, $user1->getPassword()));
            $user1Repository->add($user1);
            return $this->redirectToRoute('app_user1_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('user1/edit.html.twig', [
            'user1' => $user1,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_user1_delete', methods: ['POST'])]
    public function delete(Request $request, User1 $user1, User1Repository $user1Repository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$user1->getId(), $request->request->get('_token'))) {
            $user1Repository->remove($user1);
        }

        return $this->redirectToRoute('app_user1_index', [], Response::HTTP_SEE_OTHER);
    }

  /**
     * @Route("/{id}/delete", name="app_user1_deleteone", methods={"GET"})
     */
    public function deleteone(User1 $user1): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($user1);
        $entityManager->flush();

        return $this->redirectToRoute('app_user1_index');
    }
}
